package photo.world.domain.photosession.entity

fun interface NotificationSender {

    fun sendNotification(
        email: String,
    )
}