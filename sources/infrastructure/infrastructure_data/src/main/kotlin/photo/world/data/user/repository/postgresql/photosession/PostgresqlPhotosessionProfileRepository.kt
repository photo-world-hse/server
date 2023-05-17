package photo.world.data.user.repository.postgresql.photosession

import org.springframework.stereotype.Component
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileRepository
import photo.world.data.user.utils.toProfile
import photo.world.domain.errors.notFound
import photo.world.domain.photosession.entity.ProfileData
import photo.world.domain.photosession.repository.PhotosessionProfileRepository

@Component
class PostgresqlPhotosessionProfileRepository(
    private val springDataPostgresqlProfileRepository: SpringDataPostgresqlProfileRepository,
): PhotosessionProfileRepository {

    override fun getProfileInfo(email: String, profileType: ProfileType): ProfileData {
        val dataProfile = springDataPostgresqlProfileRepository.findProfile(email, profileType)
            ?: notFound<DataProfile>(
                "email = $email, " +
                    "profileType = ${profileType.name}",
            )
        val profile = dataProfile.toProfile()
        return ProfileData(
            userId = dataProfile.user.id,
            email = email,
            name = dataProfile.user.name,
            profileType = profileType,
            rating = profile.rating,
            avatarUrl = dataProfile.avatarUrl,
            commentsNumber = profile.commentNumber,
        )
    }
}