package photo.world.domain.auth.repository

import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.entity.Token

interface TokenRepository {

    fun findAllValidTokensByUser(username: String): List<Token>

    fun findByToken(token: String): Token?

    fun save(domainToken: Token, user: AuthUser)

    fun update(domainToken: Token)
}