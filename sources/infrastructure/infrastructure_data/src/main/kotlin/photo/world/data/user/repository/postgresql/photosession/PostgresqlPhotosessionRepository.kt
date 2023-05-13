package photo.world.data.user.repository.postgresql.photosession

import org.springframework.stereotype.Component
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.photosession.DataPhotosession
import photo.world.data.user.entity.photosession.ParticipateRelationship
import photo.world.data.user.entity.profile.DataPhoto
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.repository.spring.photsession.SpringDataPostgresqlParticipateRelationshipRepository
import photo.world.data.user.repository.spring.photsession.SpringDataPostgresqlPhotosessionRepository
import photo.world.data.user.repository.spring.photsession.SpringDataPostgresqlPhotosessionTagRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileRepository
import photo.world.data.user.utils.toDataPhotosession
import photo.world.data.user.utils.toNewDataPhotosession
import photo.world.data.user.utils.toPhotosession
import photo.world.domain.errors.notFound
import photo.world.domain.photosession.entity.Photosession
import photo.world.domain.photosession.entity.PhotosessionProfile
import photo.world.domain.photosession.repository.PhotosessionRepository
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Component
class PostgresqlPhotosessionRepository(
    private val springDataPostgresqlPhotosessionRepository: SpringDataPostgresqlPhotosessionRepository,
    private val springDataPostgresqlProfileRepository: SpringDataPostgresqlProfileRepository,
    private val springDataPostgresqlPhotosessionTagRepository: SpringDataPostgresqlPhotosessionTagRepository,
    private val springDataPostgresqlParticipateRelationship: SpringDataPostgresqlParticipateRelationshipRepository,
) : PhotosessionRepository {

    @OptIn(ExperimentalStdlibApi::class)
    override fun getById(photosessionId: String): Photosession {
        val dataPhotosession = springDataPostgresqlPhotosessionRepository.findById(photosessionId).getOrNull()
            ?: notFound<DataPhotosession>("photosessionId: $photosessionId")
        return dataPhotosession.toPhotosession()
    }

    override fun saveById(photosessionId: String, photosession: Photosession) {
        val organizer = getProfile(
            email = photosession.organizer.email,
            profileType = photosession.organizer.profileType,
        )
        val dataTags = springDataPostgresqlPhotosessionTagRepository.findAll()
        val dataPhotosession = photosession.toDataPhotosession(
            photosessionId = photosessionId,
            organizer = organizer,
            dataTags = dataTags,
        )
        dataPhotosession.setRelationships(photosession.participants)
        springDataPostgresqlPhotosessionRepository.save(dataPhotosession)
    }

    override fun saveNew(photosession: Photosession): String {
        val organizer = springDataPostgresqlProfileRepository.findProfile(
            username = photosession.organizer.email,
            profileType = photosession.organizer.profileType,
        )
            ?: notFound<DataProfile>(
                "email = ${photosession.organizer.email}, " +
                    "profileType = ${photosession.organizer.profileType.name}",
            )
        val dataPhotosession =
            springDataPostgresqlPhotosessionRepository.save(photosession.toNewDataPhotosession(organizer))
        return dataPhotosession.id
    }

    override fun removeById(photosessionId: String) {
        springDataPostgresqlPhotosessionRepository.deleteById(photosessionId)
    }

    override fun getAllInvitations(email: String): List<Pair<String, Photosession>> {
        val allInvitationsForUser = springDataPostgresqlParticipateRelationship.findAllInvitationsForUser(email)
        return allInvitationsForUser.map {
            with(it.photosession) { id to toPhotosession() }
        }
    }

    override fun getAllActiveForUser(email: String): List<Pair<String, Photosession>> {
        val photosessions = springDataPostgresqlPhotosessionRepository.findAllActivePhotosessions()
        return photosessions
            .filter { photosession -> photosession.containsUser(email) }
            .map { it.id to it.toPhotosession() }
    }

    override fun getAllArchive(email: String): List<Pair<String, Photosession>> {
        val photosessions = springDataPostgresqlPhotosessionRepository.findAllFinishedPhotosessions()
        return photosessions
            .filter { photosession -> photosession.containsUser(email) }
            .map { it.id to it.toPhotosession() }
    }

    override fun getAll(email: String): List<Pair<String, Photosession>> {
        val photosessions = springDataPostgresqlPhotosessionRepository.findAll()
        return photosessions
            .filter { photosession -> photosession.containsUser(email) }
            .map { it.id to it.toPhotosession() }
    }

    private fun DataPhotosession.containsUser(email: String): Boolean =
        organizer.user.email == email
            || participateRelationships.find { it.profile.user.email == email } != null

    private fun PhotosessionProfile.isEqual(dataProfile: DataProfile): Boolean =
        dataProfile.user.email == email && dataProfile.profileType == profileType

    private fun getProfile(email: String, profileType: ProfileType): DataProfile =
        springDataPostgresqlProfileRepository.findProfile(email, profileType)
            ?: notFound<DataProfile>("email = $email, profileType = $profileType")

    private fun DataPhotosession.setRelationships(
        participants: List<PhotosessionProfile>,
    ) {
        val participateRelationships =
            springDataPostgresqlParticipateRelationship.findAllByPhotosessionId(id).toMutableList()
        val newRelationships = participants
            .filterNot { profile ->
                participateRelationships.any {
                    profile.isEqual(it.profile) && profile.inviteStatus == it.inviteStatus
                }
            }
            .map { profile ->
                val dataProfile = getProfile(profile.email, profile.profileType)
                val relationship = ParticipateRelationship(
                    inviteStatus = profile.inviteStatus,
                    profile = dataProfile,
                    photosession = this,
                )
                springDataPostgresqlParticipateRelationship.save(relationship)
            }
        participateRelationships.addAll(newRelationships)
        this.participateRelationships = participants.map { photosessionProfile ->
            participateRelationships.find { relationship ->
                photosessionProfile.isEqual(relationship.profile)
            } ?: notFound<ParticipateRelationship>(
                "email = ${photosessionProfile.email}, " +
                    "profileType = ${photosessionProfile.profileType.name}",
            )
        }
    }
}