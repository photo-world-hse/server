package photo.world.domain.profile.entity

import photo.world.common.profile.ProfileType
import photo.world.domain.errors.DomainException
import photo.world.domain.profile.entity.profile.*
import photo.world.domain.profile.ext.getProfileIndex
import photo.world.domain.profile.service.data.ProfileData

private const val NotContainsElementIndex = -1

class Account(
    val name: String,
    val email: String,
    val telephone: String?,
    val chatAccountId: String,
    profiles: List<Profile>,
) {

    private val mutableProfiles = profiles.toMutableList()

    fun getProfile(profileType: ProfileType): Profile {
        val profileIndex = mutableProfiles.getProfileIndex(profileType)
        if (profileIndex != -1) {
            return mutableProfiles[profileIndex]
        } else {
            throw DomainException("Profile ${profileType.name.lowercase()} for model does not exist")
        }
    }

    fun addProfile(
        profileData: ProfileData,
        updateChatData: (Profile) -> Unit = {},
    ): Profile {
        val (profile, profileType) = when (profileData) {
            is ProfileData.ModelData -> ModelProfile.createNewProfile(profileData) to ProfileType.MODEL
            is ProfileData.PhotographerData ->
                PhotographerProfile.createNewProfile(profileData) to ProfileType.PHOTOGRAPHER
            is ProfileData.VisagistData -> VisagistProfile.createNewProfile(profileData) to ProfileType.VISAGIST
        }
        val isProfileExist = mutableProfiles.getProfileIndex(profileType) != -1
        if (isProfileExist) throw DomainException("${profileType.name.lowercase()} profile already exists")
        if (mutableProfiles.isEmpty()) {
            updateChatData(profile)
        }
        mutableProfiles.add(profile)
        return profile
    }

    fun deleteProfile(
        profileType: ProfileType,
        updateProfileData: (Profile?) -> Unit = {},
    ): Profile {
        val profileIndex = mutableProfiles.getProfileIndex(profileType)
        if (profileIndex != NotContainsElementIndex) {
            val removedProfile = mutableProfiles.removeAt(profileIndex)
            if (profileIndex == 0 && mutableProfiles.isEmpty()) {
                updateProfileData(null)
            } else if (profileIndex == 0) {
                updateProfileData(mutableProfiles.firstOrNull())
            }
            return removedProfile
        } else {
            throw DomainException("${profileType.name.lowercase()} profile ${profileType.name.lowercase()} does not exist")
        }
    }
}