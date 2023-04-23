package photo.world.domain.auth.entity

import photo.world.domain.auth.repository.TokenRepository
import java.util.logging.Logger

private val Logger: Logger = java.util.logging.Logger.getLogger(Token::class.simpleName)

class Token private constructor(
    val token: String,
    val tokenType: TokenType,
    expired: Boolean,
    revoked: Boolean,
) {

    var expired = expired
        private set
    var revoked = revoked
        private set

    fun revokeToken() {
        revoked = true
        Logger.fine("Token $token was revoked")
    }

    fun setTokenAsExpired() {
        expired = true
        Logger.fine("Token $token was expired")
    }

    companion object {

        fun createNewToken(
            token: String,
            user: AuthUser,
            tokenRepository: TokenRepository,
        ): Token {
            val tokens = tokenRepository.findAllValidTokensByUser(user.email)
            tokens.forEach { revokedToken ->
                revokedToken.revokeToken()
                tokenRepository.update(revokedToken)
            }
            val newToken = Token(
                token = token,
                tokenType = TokenType.BEARER,
                expired = false,
                revoked = false,
            )
            tokenRepository.save(newToken, user)
            Logger.fine("Token $token was created for user with email: ${user.email}")
            return newToken
        }

        fun createExistingToken(
            token: String,
            expired: Boolean,
            revoked: Boolean,
        ): Token = Token(
            token = token,
            tokenType = TokenType.BEARER,
            expired = expired,
            revoked = revoked,
        )
    }
}