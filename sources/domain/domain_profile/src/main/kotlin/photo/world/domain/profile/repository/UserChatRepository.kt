package photo.world.domain.profile.repository

interface UserChatRepository {

    fun getChatUrlForUsers(userEmail1: String, userEmail2: String): String?

    fun saveNewChat(userEmail1: String, userEmail2: String, chatId: String)
}