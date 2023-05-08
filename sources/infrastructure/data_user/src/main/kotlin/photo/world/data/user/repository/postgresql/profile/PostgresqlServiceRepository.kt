package photo.world.data.user.repository.postgresql.profile

import org.springframework.stereotype.Component
import photo.world.common.profile.ProfileType
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlServiceRepository
import photo.world.domain.profile.repository.ServiceRepository

@Component
class PostgresqlServiceRepository(
    val springDataPostgresqlServiceRepository: SpringDataPostgresqlServiceRepository,
): ServiceRepository {

    override fun getServices(profileType: ProfileType): List<String> =
        when (profileType) {
            ProfileType.MODEL -> springDataPostgresqlServiceRepository.findAllModelServices().map { it.name }
            ProfileType.VISAGIST -> springDataPostgresqlServiceRepository.findAllVisagistServices().map { it.name }
            ProfileType.PHOTOGRAPHER ->
                springDataPostgresqlServiceRepository.findAllPhotographerServices().map { it.name }
        }
}