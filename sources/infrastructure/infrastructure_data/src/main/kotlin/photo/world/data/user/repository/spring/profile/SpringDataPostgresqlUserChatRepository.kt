package photo.world.data.user.repository.spring.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import photo.world.data.user.entity.profile.UsersChatRelationship

interface SpringDataPostgresqlUserChatRepository : JpaRepository<UsersChatRelationship, String> {

    @Query(
        """
            select ucr from UsersChatRelationship ucr
            where (ucr.userEmail1 = :userEmail1 and ucr.userEmail2 = :userEmail2)
                or (ucr.userEmail1 = :userEmail2 and ucr.userEmail2 = :userEmail1)
        """
    )
    fun findByUserIds(userEmail1: String, userEmail2: String): UsersChatRelationship?
}