package photo.world.domain.auth.repository

import photo.world.domain.auth.entity.AuthUser

interface AuthUserRepository {

    fun findUserByEmail(email: String): AuthUser?

    fun save(user: AuthUser)

    fun delete(email: String)
}