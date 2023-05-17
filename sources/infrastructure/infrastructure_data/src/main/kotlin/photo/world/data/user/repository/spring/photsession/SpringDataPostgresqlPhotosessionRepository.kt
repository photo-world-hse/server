package photo.world.data.user.repository.spring.photsession

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.photosession.DataPhotosession

@Repository("SpringDataPostgresqlPhotosessionRepository")
interface SpringDataPostgresqlPhotosessionRepository : JpaRepository<DataPhotosession, String> {

    @Query(
        """
            select p from DataPhotosession p
            where p.isFinished = true
        """
    )
    fun findAllFinishedPhotosessions(): List<DataPhotosession>

    @Query(
        """
            select p from DataPhotosession p
            where p.isFinished = false 
        """
    )
    fun findAllActivePhotosessions(): List<DataPhotosession>
}