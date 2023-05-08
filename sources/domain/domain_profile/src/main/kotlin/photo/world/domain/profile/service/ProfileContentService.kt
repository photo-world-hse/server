package photo.world.domain.profile.service

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.profile.ModelProfile
import photo.world.domain.profile.entity.profile.Profile

interface ProfileContentService {

    fun getProfile(accountEmail: String, profileType: ProfileType): Profile

    fun getProfiles(accountEmail: String): List<Profile>

    fun getProfileName(accountEmail: String): String

    fun changeProfileAvatar(
        accountEmail: String,
        profileType: ProfileType,
        newAvatarUrl: String,
    )

    fun addPhotos(
        accountEmail: String,
        profileType: ProfileType,
        newPhotos: List<String>,
    )

    fun deletePhoto(
        accountEmail: String,
        profileType: ProfileType,
        photoUrl: String,
    )

    fun getAllPhotos(accountEmail: String, profileType: ProfileType): List<String>

    fun changeProfileInfo(
        accountEmail: String,
        profileType: ProfileType,
        aboutMe: String,
        workExperience: Int,
        additionalInfo: String,
    )

    fun changeTags(
        accountEmail: String,
        profileType: ProfileType,
        tags: List<String>,
    )

    fun changeServices(
        accountEmail: String,
        profileType: ProfileType,
        services: List<Service<*>>,
    )

    fun changeModelParams(
        accountEmail: String,
        modelParams: ModelProfile.Params,
    )
}