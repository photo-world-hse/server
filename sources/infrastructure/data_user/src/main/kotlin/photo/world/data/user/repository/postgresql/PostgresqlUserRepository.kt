package photo.world.data.user.repository.postgresql

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import photo.world.data.user.repository.spring.SpringDataPostgresqlUserRepository
import photo.world.data.user.utils.newProfileToData
import photo.world.data.user.utils.toDomain
import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.repository.AuthUserRepository

private val Logger: Logger = LoggerFactory.getLogger(PostgresqlUserRepository::class.java)

@Component
internal class PostgresqlUserRepository(
    private val springRepository: SpringDataPostgresqlUserRepository,
) : AuthUserRepository {

    override fun findUserByEmail(email: String): AuthUser? =
        springRepository.findByEmail(email)?.toDomain()

    override fun save(user: AuthUser) {
        val dataUser = user.newProfileToData()
        springRepository.save(dataUser)
        Logger.debug("User with email ${user.email} was saved")
    }

}