package photo.world.data.user.repository.spring.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.entity.profile.ProfileServiceRelationship
import photo.world.domain.profile.entity.profile.ProfileType

@Repository("SpringDataPostgresqlProfileRepository")
internal interface SpringDataPostgresqlProfileRepository : JpaRepository<DataProfile, String> {

    @Query(
        """
        select p from DataProfile p inner join BaseUser u on p.user.id = u.id
        where u.email = :username
    """
    )
    fun findAllProfilesByUser(username: String): List<DataProfile>

    @Query(
        """
        select p from DataProfile p inner join BaseUser u on p.user.id = u.id
        where u.email = :username and p.profileType = :profileType
    """
    )
    fun findProfile(username: String, profileType: ProfileType): DataProfile?

    @Query(
        """
        delete from DataProfile p
        where p.user.email = :username and p.profileType = :profileType
    """
    )
    fun deleteProfileForAccount(username: String, profileType: String)

    @Query(
        """
        select ps from ProfileServiceRelationship ps
        where ps.profile.id = :profileId
    """
    )
    fun findProfileServiceRelationships(profileId: String): List<ProfileServiceRelationship>

    @Query(
        """
            select p from DataProfile p
            where p.profileType = :profileType
                and p.user.name like concat('%',:name,'%') 
                and p.workExperience between :startWorkExperience and :endWorkExperience
        """
    )
    fun findProfilesBySearchParams(
        name: String,
        profileType: ProfileType,
        startWorkExperience: Int,
        endWorkExperience: Int,
    ): List<DataProfile>
}