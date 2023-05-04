package photo.world.data.user.repository.spring.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.profile.ProfileServiceRelationship

@Repository("SpringDataPostgresqlProfileServiceRepository")
interface SpringDataPostgresqlProfileServiceRepository : JpaRepository<ProfileServiceRelationship, String> {

    @Query("""
        select ps from ProfileServiceRelationship ps
        where ps.profile.id = :profileId
    """)
    fun getProfileServiceRelationshipByProfileId(profileId: String): List<ProfileServiceRelationship>
}