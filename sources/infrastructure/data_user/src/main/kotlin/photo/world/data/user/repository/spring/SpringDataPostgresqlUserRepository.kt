package photo.world.data.user.repository.spring

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.user.BaseUser

@Repository("SpringDataPostgresqlUserRepository")
internal interface SpringDataPostgresqlUserRepository : JpaRepository<BaseUser, String> {

    fun findByEmail(email: String): BaseUser?
}
