package photo.world.data.user.repository.postgresql.photosession

import org.springframework.stereotype.Component
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileRepository
import photo.world.domain.errors.notFound
import photo.world.domain.photosession.entity.ProfileData
import photo.world.domain.photosession.repository.PhotosessionProfileRepository

@Component
class PostgresqlPhotosessionProfileRepository(
    private val springDataPostgresqlProfileRepository: SpringDataPostgresqlProfileRepository,
): PhotosessionProfileRepository {

    override fun getProfileInfo(email: String, profileType: ProfileType): ProfileData {
        val profile = springDataPostgresqlProfileRepository.findProfile(email, profileType)
            ?: notFound<DataProfile>(
                "email = $email, " +
                    "profileType = ${profileType.name}",
            )
        return ProfileData(
            email = email,
            name = profile.user.name,
            profileType = profileType,
            rating = 5f,
            avatarUrl = profile.avatarUrl,
            commentsNumber = 10,
        )
    }
}