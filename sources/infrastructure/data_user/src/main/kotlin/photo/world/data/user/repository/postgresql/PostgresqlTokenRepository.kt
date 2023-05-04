package photo.world.data.user.repository.postgresql

import org.springframework.stereotype.Component
import photo.world.data.user.entity.token.Token
import photo.world.data.user.repository.spring.SpringDataPostgresqlTokenRepository
import photo.world.data.user.utils.toData
import photo.world.data.user.utils.toDomain
import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.repository.TokenRepository
import photo.world.domain.auth.entity.Token as DomainToken

@Component
internal class PostgresqlTokenRepository(
    private val springTokenRepository: SpringDataPostgresqlTokenRepository,
) : TokenRepository {

    override fun findAllValidTokensByUser(username: String): List<DomainToken> =
        springTokenRepository.findAllValidTokensByUser(username).map { it.toDomain() }

    override fun findByToken(token: String): DomainToken? =
        springTokenRepository.findByToken(token)?.toDomain()

    override fun save(domainToken: DomainToken, user: AuthUser) {
        val dataUser = user.toData()
        val dataToken =
            Token(
                token = domainToken.token,
                tokenType = domainToken.tokenType,
                expired = domainToken.expired,
                revoked = domainToken.revoked,
                user = dataUser,
            )
        springTokenRepository.save(dataToken)
    }

    override fun update(domainToken: DomainToken) {
        val dataToken = springTokenRepository.findByToken(domainToken.token) ?: TODO("Token not exist")
        val newDataToken = dataToken.copy(
            revoked = domainToken.revoked,
            expired = domainToken.expired,
        )
        springTokenRepository.save(newDataToken)
    }
}