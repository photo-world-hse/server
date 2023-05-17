package photo.world.security.auth

import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.entity.Token
import photo.world.domain.auth.repository.AuthUserRepository
import photo.world.domain.auth.repository.TokenRepository
import photo.world.domain.auth.service.AuthService
import photo.world.domain.auth.service.ChatUserCreatorService
import photo.world.domain.auth.service.TokenService
import photo.world.domain.auth.service.data.AuthData
import photo.world.domain.errors.SendbirdException
import photo.world.domain.errors.notFound
import photo.world.domain.mail.EmailService
import java.lang.IllegalStateException

@Service
class AuthServiceImpl(
    private val userRepository: AuthUserRepository,
    private val tokenRepository: TokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: TokenService,
    private val emailService: EmailService,
    private val authenticationManager: AuthenticationManager,
    private val chatUserCreatorService: ChatUserCreatorService,
) : AuthService {

    @Value("\${sendbird.appId}")
    private lateinit var appId: String

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

    override fun authenticate(email: String, password: String): AuthData {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        val user = userRepository.findUserByEmail(email)
            ?: notFound<AuthUser>("email = $email")
        if (!user.isActivatedUser) error("User with email: $email not activated")
        val jwtToken = jwtService.generateToken(user)
        val token = Token.createNewToken(jwtToken, user, tokenRepository)
        return AuthData(
            sessionToken = token.token,
            chatAccessToken = user.chatAccessToken
                ?: throw IllegalStateException("user don't have chat access token"),
            chatUserId = user.id,
            sendbirdAppId = appId,
        )
    }

    override fun resendVerifyCode(email: String) {
        val user = userRepository.findUserByEmail(email) ?: notFound<AuthUser>("email = $email")
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
    ): AuthData {
        val user = userRepository.findUserByEmail(email) ?: notFound<AuthUser>("email = $email")
        user.activateUser(activationCode)
        val jwtToken = jwtService.generateToken(user)
        val token = Token.createNewToken(jwtToken, user, tokenRepository)
        val chatAccessToken = chatUserCreatorService.createChatUserForUser(user.id, user.name)
            ?: throw SendbirdException("Failed to create chat for user")
        user.chatAccessToken = chatAccessToken
        userRepository.save(user)
        return AuthData(
            sessionToken = token.token,
            chatAccessToken = chatAccessToken,
            chatUserId = user.id,
            sendbirdAppId = appId,
        )
    }

    override fun logout(email: String) {
        val user = userRepository.findUserByEmail(email) ?: notFound<AuthUser>("email = $email")
        user.logout(tokenRepository)
    }
}