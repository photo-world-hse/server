package photo.world.data.user.repository.spring.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.profile.DataService

@Repository("SpringDataPostgresqlServiceRepository")
interface SpringDataPostgresqlServiceRepository : JpaRepository<DataService, String> {

    @Query(
        """
        select s from DataService s
        where s.isPhotographerService = true
    """
    )
    fun findAllPhotographerServices(): List<DataService>

    @Query(
        """
        select s from DataService s
        where s.isVisagistService = true
    """
    )
    fun findAllVisagistServices(): List<DataService>

    @Query(
        """
        select s from DataService s
        where s.isModelService = true
    """
    )
    fun findAllModelServices(): List<DataService>
}