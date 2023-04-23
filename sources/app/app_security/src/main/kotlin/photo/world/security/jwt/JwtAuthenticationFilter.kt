package photo.world.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private const val AuthorizationHeader = "Authorization"
private const val BearerTokenPrefix = "Bearer "
internal const val CurrentUserAttribute = "currentUser"

@Component
internal class JwtAuthenticationFilter @Autowired constructor(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = getJwt(request)
        if (jwt != null && jwtService.validateJwtToken(jwt)) {
            val username = jwtService.getUserNameFromJwtToken(jwt)
            val userDetails = userDetailsService.loadUserByUsername(username)
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
            request.setAttribute(CurrentUserAttribute, username)
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwt(request: HttpServletRequest): String? {
        val authHeader = request.getHeader(AuthorizationHeader)
        return if (authHeader != null && authHeader.startsWith(BearerTokenPrefix)) {
            authHeader.substringAfter(BearerTokenPrefix)
        } else {
            null
        }
    }
}