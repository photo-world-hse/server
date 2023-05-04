package photo.world.security.auth

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.entity.Token
import photo.world.domain.auth.repository.AuthUserRepository
import photo.world.domain.auth.repository.TokenRepository
import photo.world.domain.auth.service.AuthService
import photo.world.domain.auth.service.TokenService
import photo.world.domain.mail.EmailService

@Service
class AuthService(
    private val userRepository: AuthUserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: TokenService,
    private val emailService: EmailService,
    private val authenticationManager: AuthenticationManager,
) : AuthService {

    override fun register(email: String, name: String, password: String) {
        val user = AuthUser.createNewUser(
            name = name,
            password = passwordEncoder.encode(password),
            email = email,
            userRepository = userRepository,
        )
        userRepository.save(user)
        emailService.sendMessage(
            username = user.name,
            userEmail = user.email,
            activationCode = user.activationCode,
        )
    }

    override fun authenticate(email: String, password: String): Token {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        val user = userRepository.findUserByEmail(email)
            ?: error("User with email: $email not found in database")
        if (!user.isActivatedUser) error("User with email: $email not activated")
        val jwtToken = jwtService.generateToken(user)
        return Token.createNewToken(jwtToken, user, tokenRepository)
    }

    override fun resendVerifyCode(email: String) {
        val user = userRepository.findUserByEmail(email) ?: error("User with email $email not found")
        user.createNewActivationCode()
        userRepository.save(user)
        emailService.sendMessage(
            username = user.name,
            userEmail = user.email,
            activationCode = user.activationCode,
        )
    }

    override fun verify(
        email: String,
        activationCode: String,
    ): Token {
        val user = userRepository.findUserByEmail(email) ?: error("User with email $email not found")
        user.activateUser(activationCode)
        val jwtToken = jwtService.generateToken(user)
        userRepository.save(user)
        return Token.createNewToken(jwtToken, user, tokenRepository)
    }

    override fun logout(email: String) {
        val user = userRepository.findUserByEmail(email) ?: error("User with email $email not found")
        user.logout(tokenRepository)
    }
}