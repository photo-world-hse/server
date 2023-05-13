package photo.world.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.photosession.DataPhotosession
import photo.world.data.user.entity.photosession.DataPhotosessionTag
import photo.world.data.user.entity.photosession.ParticipateRelationship
import photo.world.data.user.entity.profile.DataPhoto
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.entity.profile.DataService
import photo.world.data.user.entity.profile.DataTag
import photo.world.data.user.entity.user.BaseUser
import photo.world.data.user.repository.spring.SpringDataPostgresqlUserRepository
import photo.world.data.user.repository.spring.photsession.SpringDataPostgresqlPhotosessionRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlServiceRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlTagRepository
import photo.world.domain.photosession.entity.InviteStatus
import java.util.*

@Configuration
class DatabaseConfig {

    @Bean
    fun databaseCommandLineRunner(
        springDataPostgresqlTagRepository: SpringDataPostgresqlTagRepository,
        springDataPostgresqlServiceRepository: SpringDataPostgresqlServiceRepository,
        springPhotosessionTagRepository: photo.world.data.user.repository.spring.photsession.SpringDataPostgresqlPhotosessionTagRepository,
        springDataPostgresqlUserRepository: SpringDataPostgresqlUserRepository,
        springProfileRepository: SpringDataPostgresqlProfileRepository,
        springDataPostgresqlPhotosessionRepository: SpringDataPostgresqlPhotosessionRepository,
        passwordEncoder: PasswordEncoder,
    ) = CommandLineRunner {
        var modelTagIndex = 1
        val modelTags = generateSequence {
            DataTag(
                name = "model tag ${modelTagIndex++}",
                profiles = listOf(),
                isModelTag = true,
            )
        }.take(10).toList()
        var photographerTagIndex = 1
        val photographerTags = generateSequence {
            DataTag(
                name = "photographer tag ${photographerTagIndex++}",
                profiles = listOf(),
                isPhotographerTag = true,
            )
        }.take(10).toList()
        var visagistTagIndex = 1
        val visagistTags = generateSequence {
            DataTag(
                name = "visagist tag ${visagistTagIndex++}",
                profiles = listOf(),
                isVisagistTag = true,
            )
        }.take(10).toList()
        springDataPostgresqlTagRepository.saveAll(modelTags)
        springDataPostgresqlTagRepository.saveAll(photographerTags)
        springDataPostgresqlTagRepository.saveAll(visagistTags)

        var modelServiceIndex = 1
        val modelServices = generateSequence {
            DataService(
                name = "model service ${modelServiceIndex++}",
                isModelService = true,
            )
        }.take(10).toList()
        var photographerServiceIndex = 1
        val photographerServices = generateSequence {
            DataService(
                name = "photographer service ${photographerServiceIndex++}",
                isPhotographerService = true,
            )
        }.take(10).toList()
        var visagistServiceIndex = 1
        val visagistServices = generateSequence {
            DataService(
                name = "visagist service ${visagistServiceIndex++}",
                isVisagistService = true,
            )
        }.take(10).toList()
        springDataPostgresqlServiceRepository.saveAll(modelServices)
        springDataPostgresqlServiceRepository.saveAll(photographerServices)
        springDataPostgresqlServiceRepository.saveAll(visagistServices)

        var photosessionTagIndex = 1
        val photosessionTags = generateSequence {
            DataPhotosessionTag(
                name = "photosession tag ${photosessionTagIndex++}",
                photosessions = listOf(),
            )
        }.take(10).toList()
        springPhotosessionTagRepository.saveAll(photosessionTags)

        val user1 = springDataPostgresqlUserRepository.save(
            BaseUser(
                id = "test_user_1",
                email = "test@test.com",
                name = "Test User",
                activationCode = "123456",
                password = passwordEncoder.encode("123456"),
                isActivatedUser = true,
            )
        )

        val user2 = springDataPostgresqlUserRepository.save(
            BaseUser(
                id = "test_user_2",
                email = "test2@test.com",
                name = "Test User 2",
                activationCode = "123456",
                password = passwordEncoder.encode("123456"),
                isActivatedUser = true,
            )
        )

        val profile1 = springProfileRepository.save(
            DataProfile(
                user = user1,
                profileType = ProfileType.PHOTOGRAPHER,
                avatarUrl = "",
                photos = listOf(),
                albums = listOf(),
                aboutMe = "",
                workExperience = 1,
                additionalInfo = "",
                comments = listOf(),
            )
        )

        val profile2 =
            springProfileRepository.save(
                DataProfile(
                    user = user2,
                    profileType = ProfileType.PHOTOGRAPHER,
                    avatarUrl = "",
                    photos = listOf(),
                    albums = listOf(),
                    aboutMe = "",
                    workExperience = 1,
                    additionalInfo = "",
                    comments = listOf(),
                )
            )

        val photosession =
            springDataPostgresqlPhotosessionRepository.save(
                DataPhotosession(
                    name = "Test photosession",
                    description = "Photosession description",
                    duration = 3.5f,
                    address = "Moscow",
                    startDateAndTime = Date(),
                    organizer = profile1,
                    isFinished = false,
                    photos = listOf(
                        DataPhoto(url = "test image 2"),
                    )
                )
            )

        photosession.participateRelationships = listOf(
            ParticipateRelationship(
                inviteStatus = InviteStatus.PENDING,
                profile = profile2,
                photosession = photosession,
            )
        )
        springDataPostgresqlPhotosessionRepository.save(photosession)
    }
}