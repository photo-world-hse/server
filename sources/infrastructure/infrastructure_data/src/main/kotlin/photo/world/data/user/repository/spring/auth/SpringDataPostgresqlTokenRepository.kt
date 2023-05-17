package photo.world.data.user.repository.spring.auth

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.token.Token

@Repository("SpringDataPostgresqlTokenRepository")
internal interface SpringDataPostgresqlTokenRepository : JpaRepository<Token, String> {

    @Query("""
       select t from Token t inner join BaseUser u on t.user.id = u.id
       where u.email = :username and (t.expired = false and t.revoked = false )
    """)
    fun findAllValidTokensByUser(username: String): List<Token>

    fun findByToken(token: String): Token?
}