package photo.world.domain.profile.service

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.profile.*
import photo.world.domain.profile.service.data.ProfileData

interface ProfileService {

    fun createNewProfile(
        accountEmail: String,
        profileData: ProfileData,
    ): Profile

    fun deleteProfile(accountEmail: String, profileType: ProfileType)

    fun getPersonalChatWithUser(
        initializerEmail: String,
        participantEmail: String,
    ): String
}