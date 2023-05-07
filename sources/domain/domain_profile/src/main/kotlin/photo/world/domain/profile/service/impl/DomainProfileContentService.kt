package photo.world.domain.profile.service.impl

import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.profile.ModelProfile
import photo.world.domain.profile.entity.profile.Profile
import photo.world.domain.profile.entity.profile.ProfileType
import photo.world.domain.profile.repository.AccountRepository
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.service.ProfileContentService

class DomainProfileContentService(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository,
) : ProfileContentService {

    override fun getProfile(accountEmail: String, profileType: ProfileType): Profile {
        val account = accountRepository.getAccount(accountEmail)
        return account.getProfile(profileType)
    }

    override fun getProfiles(accountEmail: String): List<Profile> =
        accountRepository.getProfilesForAccount(accountEmail)

    override fun getProfileName(accountEmail: String): String = accountRepository.getAccount(accountEmail).name

    override fun changeProfileAvatar(accountEmail: String, profileType: ProfileType, newAvatarUrl: String) {
        val profile = getProfile(accountEmail, profileType)
        profile.changeAvatar(newAvatarUrl)
        profileRepository.update(accountEmail, profile)
    }

    override fun addPhotos(accountEmail: String, profileType: ProfileType, newPhotos: List<String>) {
        val profile = getProfile(accountEmail, profileType)
        profile.addPhotos(newPhotos)
        profileRepository.update(accountEmail, profile)
    }

    override fun deletePhoto(accountEmail: String, profileType: ProfileType, photoUrl: String) {
        val profile = getProfile(accountEmail, profileType)
        profile.deletePhoto(photoUrl)
        profileRepository.update(accountEmail, profile)
    }

    override fun getAllPhotos(accountEmail: String, profileType: ProfileType): List<String> {
        val profile = getProfile(accountEmail, profileType)
        return profile.getAllPhotos()
    }

    override fun changeProfileInfo(
        accountEmail: String,
        profileType: ProfileType,
        aboutMe: String,
        workExperience: Int,
        additionalInfo: String
    ) {
        val profile = getProfile(accountEmail, profileType)
        profile.changeProfileInfo(
            aboutMe = aboutMe,
            workExperience = workExperience,
            additionalInfo = additionalInfo,
        )
        profileRepository.update(accountEmail, profile)
    }

    override fun changeTags(accountEmail: String, profileType: ProfileType, tags: List<String>) {
        val profile = getProfile(accountEmail, profileType)
        profile.changeTags(tags)
        profileRepository.update(accountEmail, profile)
    }

    override fun changeServices(accountEmail: String, profileType: ProfileType, services: List<Service<*>>) {
        val profile = getProfile(accountEmail, profileType)
        profile.changeServices(services)
        profileRepository.update(accountEmail, profile)
    }

    override fun changeModelParams(accountEmail: String, modelParams: ModelProfile.Params) {
        val profile = getProfile(accountEmail, ProfileType.MODEL)
        (profile as ModelProfile).changeModelParams(modelParams)
        profileRepository.update(accountEmail, profile)
    }
}