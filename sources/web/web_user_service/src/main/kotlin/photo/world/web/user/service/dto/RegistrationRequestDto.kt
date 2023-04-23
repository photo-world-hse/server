package photo.world.web.user.service.dto

internal data class RegistrationRequestDto(
    val name: String,
    val email: String,
    val password: String,
)