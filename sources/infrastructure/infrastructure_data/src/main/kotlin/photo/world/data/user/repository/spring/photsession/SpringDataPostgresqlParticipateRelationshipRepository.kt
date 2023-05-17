package photo.world.data.user.repository.spring.photsession

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.photosession.ParticipateRelationship
import photo.world.data.user.entity.profile.DataProfile

@Repository("SpringDataPostgresqlParticipateRelationshipRepository")
interface SpringDataPostgresqlParticipateRelationshipRepository : JpaRepository<ParticipateRelationship, String> {

    @Query(
        """
            select pr from ParticipateRelationship pr
            where pr.photosession.id = :photosessionId
        """
    )
    fun findAllByPhotosessionId(photosessionId: String): List<ParticipateRelationship>

    @Query(
        """
            select pr from ParticipateRelationship pr inner join DataProfile p on pr.profile.id = p.id
            where pr.profile.user.email = :email
        """
    )
    fun findAllInvitationsForUser(email: String): List<ParticipateRelationship>
}