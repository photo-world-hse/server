package photo.world.data.user.repository.postgresql.photosession

import org.springframework.stereotype.Component
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.repository.spring.photsession.SpringDataPostgresqlPhotosessionTagRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileRepository
import photo.world.domain.errors.notFound
import photo.world.domain.photosession.entity.ProfileData
import photo.world.domain.photosession.repository.PhotosessionProfileRepository
import photo.world.domain.photosession.repository.PhotosessionTagsRepository

@Component
class PostgresqlPhotosessionTagsRepository(
    private val springDataPostgresqlPhotosessionTagRepository: SpringDataPostgresqlPhotosessionTagRepository,
) : PhotosessionTagsRepository {

    override fun getAllTags(): List<String> =
        springDataPostgresqlPhotosessionTagRepository.findAll().map { it.name }
}