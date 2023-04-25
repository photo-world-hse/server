package photo.world.web.user.service.utils

import photo.world.web.user.service.dto.AuthRequestDto
import photo.world.web.user.service.dto.RegistrationRequestDto
import photo.world.web.user.service.dto.ResendVerifyCodeRequestDto
import photo.world.web.user.service.dto.VerifyRequestDto

internal object AuthValidator {

    fun validateRegistrationRequestDto(
        request: RegistrationRequestDto,
    ): Boolean =
        request.email.isEmailValid()
            && request.name.split(" ").size <= 3

    fun validateAuthRequestDto(
        request: AuthRequestDto,
    ): Boolean = request.email.isEmailValid()

    fun validateVerifyRequestDto(
        request: VerifyRequestDto,
    ): Boolean = request.email.isEmailValid() && request.activationCode.length == 6

    fun validateResendRequestDto(
        request: ResendVerifyCodeRequestDto,
    ): Boolean = request.email.isEmailValid()

    private fun String.isEmailValid(): Boolean {
        val regexPattern = Regex("^(.+)@(\\S+)$")
        return regexPattern.matches(this)
    }
}