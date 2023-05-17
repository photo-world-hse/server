package photo.world.domain.photosession.service

interface ChatService {

    fun createChat(
        photosessionName: String,
        userId: String,
        chatUrl: String,
    )

    fun addToChat(
        chatUrl: String,
        userId: String,
    )
}