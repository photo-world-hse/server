package photo.world.domain.profile.service

interface ChatProfileService {

    fun setProfileImage(
        userId: String,
        avatarUrl: String?,
    )

    fun createPrivateChatForUsers(
        chatUrl: String,
        chatName: String,
        initializerUserId: String,
        participantUserId: String
    )
}