package photo.world.data.user.repository.spring

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import photo.world.data.user.entity.user.BaseUser

@Repository("SpringDataPostgresqlUserRepository")
interface SpringDataPostgresqlUserRepository : JpaRepository<BaseUser, String> {

    fun findByEmail(email: String): BaseUser?

    @Modifying
    @Transactional
    @Query(
        """
            delete from BaseUser u
            where u.email = :email
        """
    )
    fun deleteByEmail(email: String)
}
