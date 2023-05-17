package photo.world.domain.auth.service.data

data class AuthData(
    val sessionToken: String,
    val chatAccessToken: String,
    val chatUserId: String,
    val sendbirdAppId: String,
    val username: String,
)