package photo.world.data.user.repository.spring.photsession

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import photo.world.data.user.entity.photosession.DataPhotosessionTag
import photo.world.data.user.entity.profile.DataTag

@Repository("SpringPhotosessionTagRepository")
interface SpringDataPostgresqlPhotosessionTagRepository : JpaRepository<DataPhotosessionTag, String>