package photo.world.security.jwt

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.apache.commons.lang3.RandomStringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.crypto.SecretKey

private val Logger: Logger = LoggerFactory.getLogger(JwtService::class.java)
private val JwtSignSecret: String = RandomStringUtils.random(32, true, true)

@Component
internal class JwtService {

    private val signInKey: SecretKey by lazy {
        val keyBytes = Decoders.BASE64.decode(JwtSignSecret)
        Keys.hmacShaKeyFor(keyBytes)
    }

    fun getUserNameFromJwtToken(token: String?): String =
        Jwts.parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body.subject

    fun validateJwtToken(authToken: String?): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(signInKey)
                .build()
                .parseClaimsJws(authToken)
            return true
        } catch (e: SecurityException) {
            Logger.debug("Invalid JWT signature -> Message: ${e.message}")
        } catch (e: MalformedJwtException) {
            Logger.debug("Invalid JWT token -> Message: ${e.message}")
        } catch (e: ExpiredJwtException) {
            Logger.debug("Expired JWT token -> Message: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            Logger.debug("Unsupported JWT token -> Message: ${e.message}")
        } catch (e: IllegalArgumentException) {
            Logger.debug("JWT claims string is empty -> Message: ${e.message}")
        }
        return false
    }
}
