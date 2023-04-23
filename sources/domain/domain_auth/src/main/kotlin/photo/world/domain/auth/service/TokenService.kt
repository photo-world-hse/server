package photo.world.domain.auth.service

import photo.world.domain.auth.entity.AuthUser

interface TokenService {

    fun getUserNameFromJwtToken(token: String): String

    fun validateJwtToken(authToken: String): Boolean

    fun generateToken(
        userDetails: AuthUser,
        extraClaims: Map<String, Any> = mapOf(),
    ): String
}