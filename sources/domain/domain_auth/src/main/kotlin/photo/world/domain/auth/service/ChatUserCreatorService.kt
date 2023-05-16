package photo.world.domain.auth.service

interface ChatUserCreatorService {

    fun createChatUserForUser(
        id: String,
        name: String,
    ): String?
}