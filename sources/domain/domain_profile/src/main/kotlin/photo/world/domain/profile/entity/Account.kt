package photo.world.domain.profile.entity

import photo.world.domain.errors.DomainException
import photo.world.domain.profile.entity.profile.*
import photo.world.domain.profile.ext.getProfileIndex
import photo.world.domain.profile.service.data.ProfileData

class Account(
    val name: String,
    val email: String,
    val telephone: String?,
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

    fun addProfile(profileData: ProfileData): Profile {
        val (profile, profileType) = when (profileData) {
            is ProfileData.ModelData -> ModelProfile.createNewProfile(profileData) to ProfileType.MODEL
            is ProfileData.PhotographerData ->
                PhotographerProfile.createNewProfile(profileData) to ProfileType.PHOTOGRAPHER
            is ProfileData.VisagistData -> VisagistProfile.createNewProfile(profileData) to ProfileType.VISAGIST
        }
        val isProfileExist = mutableProfiles.getProfileIndex(profileType) != -1
        if (isProfileExist) throw DomainException("${profileType.name.lowercase()} profile already exists")
        mutableProfiles.add(profile)
        return profile
    }

    fun deleteProfile(profileType: ProfileType): Profile {
        val profileIndex = mutableProfiles.getProfileIndex(profileType)
        if (profileIndex != -1) {
            return mutableProfiles.removeAt(profileIndex)
        } else {
            throw DomainException("${profileType.name.lowercase()} profile ${profileType.name.lowercase()} does not exist")
        }
    }
}