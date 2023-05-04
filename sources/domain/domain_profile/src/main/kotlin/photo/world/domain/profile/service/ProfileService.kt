package photo.world.domain.profile.service

import photo.world.domain.profile.entity.profile.*
import photo.world.domain.profile.service.data.ProfileData

interface ProfileService {

    fun createNewProfile(
        accountEmail: String,
        profileData: ProfileData,
    ): Profile

    fun deleteProfile(accountEmail: String, profileType: ProfileType)
}