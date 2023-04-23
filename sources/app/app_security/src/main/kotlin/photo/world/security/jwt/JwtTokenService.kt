package photo.world.security.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.repository.TokenRepository
import photo.world.domain.auth.service.TokenService
import java.util.*
import javax.crypto.SecretKey

private val Logger: Logger = LoggerFactory.getLogger(JwtTokenService::class.java)
private val JwtSignSecret: String = RandomStringUtils.random(64, true, true)
private const val MillsInDay = 1000 * 60 * 60 * 24

@Component
class JwtTokenService(
    private val tokenRepository: TokenRepository,
) : TokenService {

    private val signInKey: SecretKey by lazy {
        val keyBytes = Decoders.BASE64.decode(JwtSignSecret)
        Keys.hmacShaKeyFor(keyBytes)
    }

    override fun getUserNameFromJwtToken(token: String): String =
        Jwts.parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body.subject

    override fun validateJwtToken(authToken: String): Boolean {
        try {
            val username = getUserNameFromJwtToken(authToken)
            val validTokens = tokenRepository.findAllValidTokensByUser(username)
            return validTokens.any { it.token == authToken }
        } catch (e: SecurityException) {
            Logger.debug("Invalid JWT signature -> Message: ${e.message}")
        } catch (e: MalformedJwtException) {
            Logger.debug("Invalid JWT token -> Message: ${e.message}")
        } catch (e: ExpiredJwtException) {
            Logger.debug("Expired JWT token -> Message: ${e.message}")
            val domainToken = tokenRepository.findByToken(authToken)
            domainToken?.setTokenAsExpired()
            domainToken?.let { tokenRepository.update(domainToken) }
        } catch (e: UnsupportedJwtException) {
            Logger.debug("Unsupported JWT token -> Message: ${e.message}")
        } catch (e: IllegalArgumentException) {
            Logger.debug("JWT claims string is empty -> Message: ${e.message}")
        }
        return false
    }

    override fun generateToken(
        userDetails: AuthUser,
        extraClaims: Map<String, Any>,
    ): String = Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(userDetails.email)
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + MillsInDay))
        .signWith(signInKey, SignatureAlgorithm.HS256)
        .compact()
}
