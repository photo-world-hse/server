package photo.world.web.user.service.utils

import photo.world.web.user.service.dto.AuthRequestDto
import photo.world.web.user.service.dto.RegistrationRequestDto
import photo.world.web.user.service.dto.ResendVerifyCodeRequestDto
import photo.world.web.user.service.dto.VerifyRequestDto

internal object AuthValidator {

    fun validateRegistrationRequestDto(
        request: RegistrationRequestDto,
    ): Boolean {
        // TODO: validate logic
        return true
    }

    fun validateAuthRequestDto(
        request: AuthRequestDto,
    ): Boolean {
        // TODO: validate logic
        return true
    }

    fun validateVerifyRequestDto(
        request: VerifyRequestDto,
    ): Boolean {
        // TODO: validate logic
        return true
    }

    fun validateResendRequestDto(
        request: ResendVerifyCodeRequestDto,
    ): Boolean {
        // TODO: validate logic
        return true
    }
}