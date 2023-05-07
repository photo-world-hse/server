package photo.world.data.user.repository.postgresql.profile

import org.springframework.stereotype.Component
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.entity.profile.ProfileServiceRelationship
import photo.world.data.user.entity.user.BaseUser
import photo.world.data.user.ext.getProfileType
import photo.world.data.user.repository.spring.SpringDataPostgresqlUserRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileServiceRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlServiceRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlTagRepository
import photo.world.data.user.utils.newProfileToData
import photo.world.data.user.utils.toProfileServiceRelationship
import photo.world.data.user.utils.updateProfileData
import photo.world.domain.errors.NotFoundEntityException
import photo.world.domain.errors.notFound
import photo.world.domain.profile.entity.profile.Profile
import photo.world.domain.profile.entity.profile.ProfileType
import photo.world.domain.profile.repository.ProfileRepository

@Component
internal class PostgresqlProfileRepository(
    private val springDataPostgresqlUserRepository: SpringDataPostgresqlUserRepository,
    private val springProfileRepository: SpringDataPostgresqlProfileRepository,
    private val springDataPostgresqlTagRepository: SpringDataPostgresqlTagRepository,
    private val springDataPostgresqlProfileServiceRepository: SpringDataPostgresqlProfileServiceRepository,
    private val springDataPostgresqlServiceRepository: SpringDataPostgresqlServiceRepository,
) : ProfileRepository {

    override fun saveNew(accountEmail: String, profile: Profile) {
        val user = springDataPostgresqlUserRepository.findByEmail(accountEmail)
            ?: notFound<BaseUser>("email: $accountEmail")
        val allTags = getAllTagsByProfileType(profile.getProfileType())
        val newProfileEntity = springProfileRepository.save(profile.newProfileToData(user, allTags))
        val dataServices = getAllServicesByProfileType(profile.getProfileType())
        try {
            val profileServiceRelationships = profile.services.map { service ->
                service.toProfileServiceRelationship(
                    dataProfile = newProfileEntity,
                    dataServices = dataServices,
                )
            }
            springDataPostgresqlProfileServiceRepository.saveAll(profileServiceRelationships)
        } catch (e: NotFoundEntityException) {
            springProfileRepository.delete(newProfileEntity)
        }
    }

    override fun update(accountEmail: String, profile: Profile) {
        val profileTypeName = profile.getProfileType()
        val allTags = getAllTagsByProfileType(profileTypeName)
        val dataProfile = springProfileRepository.findProfile(accountEmail, profileTypeName)
            ?: notFound<DataProfile>("email: $accountEmail, profile type: ${profileTypeName.name}")
        val updatedDataProfile = dataProfile.updateProfileData(profile, allTags)
        updateProfileServiceRelationships(profile, dataProfile)
        springProfileRepository.save(updatedDataProfile)
    }

    override fun delete(accountEmail: String, profile: Profile) {
        springProfileRepository.deleteProfileForAccount(accountEmail, profile.getProfileType().name)
    }

    private fun getAllTagsByProfileType(profileType: ProfileType) =
        when (profileType) {
            ProfileType.MODEL -> springDataPostgresqlTagRepository.findAllModelTags()
            ProfileType.PHOTOGRAPHER -> springDataPostgresqlTagRepository.findAllPhotographerTags()
            ProfileType.VISAGIST -> springDataPostgresqlTagRepository.findAllVisagistTags()
        }

    private fun getAllServicesByProfileType(profileType: ProfileType) =
        when (profileType) {
            ProfileType.MODEL -> springDataPostgresqlServiceRepository.findAllModelServices()
            ProfileType.PHOTOGRAPHER -> springDataPostgresqlServiceRepository.findAllPhotographerServices()
            ProfileType.VISAGIST -> springDataPostgresqlServiceRepository.findAllVisagistServices()
        }

    private fun updateProfileServiceRelationships(
        profile: Profile,
        dataProfile: DataProfile,
    ) {
        val profileServiceRelationships =
            springDataPostgresqlProfileServiceRepository.getProfileServiceRelationshipByProfileId(dataProfile.id)
        removeDeletedProfileServicesRelationships(profile, profileServiceRelationships)
        saveNewAndUpdatedProfileServiceRelationships(profile, dataProfile)
    }

    private fun removeDeletedProfileServicesRelationships(
        profile: Profile,
        profileServiceRelationships: List<ProfileServiceRelationship>,
    ) {
        val removedRelationships = profileServiceRelationships
            .filter { profileServiceRelationship ->
                profile.services.all { it.name != profileServiceRelationship.service.name }
            }
        springDataPostgresqlProfileServiceRepository.deleteAll(removedRelationships)
    }

    private fun saveNewAndUpdatedProfileServiceRelationships(
        profile: Profile,
        dataProfile: DataProfile,
    ) {
        val dataServices = getAllServicesByProfileType(profile.getProfileType())
        val newRelationships = profile.services.map { it.toProfileServiceRelationship(dataProfile, dataServices) }
        springDataPostgresqlProfileServiceRepository.saveAll(newRelationships)
    }
}