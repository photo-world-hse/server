package photo.world.security.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

private val Logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint::class.java)

internal class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?,
    ) {
        authException?.message?.let { errorMessage ->
            Logger.error("Unauthorized error. Message - $errorMessage")
        }
        response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized")
    }
}