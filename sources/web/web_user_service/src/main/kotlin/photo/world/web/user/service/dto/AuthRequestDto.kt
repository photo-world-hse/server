package photo.world.web.user.service.dto

internal data class AuthRequestDto(
    val email: String,
    val password: String,
)