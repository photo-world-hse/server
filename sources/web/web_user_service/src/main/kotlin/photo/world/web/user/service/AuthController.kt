package photo.world.web.user.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import photo.world.domain.auth.entity.Token
import photo.world.domain.auth.service.AuthService
import photo.world.domain.errors.withValidation
import photo.world.web.user.service.dto.AuthRequestDto
import photo.world.web.user.service.dto.RegistrationRequestDto
import photo.world.web.user.service.dto.ResendVerifyCodeRequestDto
import photo.world.web.user.service.dto.VerifyRequestDto
import photo.world.web.user.service.utils.AuthValidator

@RestController
@RequestMapping("/api/v1/auth")
internal class AuthController @Autowired constructor(
    private val authService: AuthService,
) {

    @PostMapping("/register")
    fun register(
        @RequestBody request: RegistrationRequestDto,
    ): ResponseEntity<Unit> =
        withValidation(request, AuthValidator::validateRegistrationRequestDto) {
            authService.register(
                email = request.email,
                name = request.name,
                password = request.password,
            )
            return ResponseEntity.ok().build()
        }

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthRequestDto,
    ): ResponseEntity<Token> =
        withValidation(request, AuthValidator::validateAuthRequestDto) {
            val token = authService.authenticate(
                email = request.email,
                password = request.password,
            )
            return ResponseEntity.ok(token)
        }

    @PostMapping("/resend_code")
    fun resendCode(
        @RequestBody request: ResendVerifyCodeRequestDto,
    ): ResponseEntity<Unit> =
        withValidation(request, AuthValidator::validateResendRequestDto) {
            authService.resendVerifyCode(email = request.email)
            return ResponseEntity.ok().build()
        }

    @PostMapping("/verify")
    fun verify(
        @RequestBody request: VerifyRequestDto,
    ): ResponseEntity<Token> =
        withValidation(request, AuthValidator::validateVerifyRequestDto) {
            val token = authService.verify(
                email = request.email,
                activationCode = request.activationCode,
            )
            return ResponseEntity.ok(token)
        }

    @DeleteMapping("/logout")
    fun logout(
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        authService.logout(authentication.name)
        return ResponseEntity.ok().build()
    }
}