package photo.world.data.user.repository.postgresql.profile

import org.springframework.stereotype.Component
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlTagRepository
import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.entity.profile.ProfileType
import photo.world.domain.profile.repository.TagRepository

@Component
class PostgresqlTagRepository(
    private val springDataPostgresqlTagRepository: SpringDataPostgresqlTagRepository,
): TagRepository {

    override fun getTags(profileType: ProfileType): List<Tag> =
        when (profileType) {
            ProfileType.MODEL ->
                springDataPostgresqlTagRepository.findAllModelTags().map { Tag(name = it.name) }
            ProfileType.PHOTOGRAPHER ->
                springDataPostgresqlTagRepository.findAllPhotographerTags().map { Tag(name = it.name) }
            ProfileType.VISAGIST ->
                springDataPostgresqlTagRepository.findAllVisagistTags().map { Tag(name = it.name) }
        }
}