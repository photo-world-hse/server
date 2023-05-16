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

        val modelPhotographerTagNames = listOf(
            "Обучение",
            "Репортажная съемка",
            "Студийная съемка",
            "Ню фотография",
            "Свадебная съемка",
            "Семейная съемка",
            "Репортажная съемка",
            "Выездная фотосъемка",
            "Цветокорекция",
        )
        val modelPhotographerTags = modelPhotographerTagNames.map { name ->
            DataTag(
                name = name,
                profiles = listOf(),
                isPhotographerTag = true,
                isModelTag = true,
            )
        }

        val visagistTagNames = listOf(
            "Создание причесок",
            "Боди-арт",
            "Пластический гримм",
            "Обучение",
        )
        val visagistTags = visagistTagNames.map { name ->
            DataTag(
                name = name,
                profiles = listOf(),
                isVisagistTag = true,
            )
        }
        springDataPostgresqlTagRepository.saveAll(modelPhotographerTags)
        springDataPostgresqlTagRepository.saveAll(visagistTags)
        springDataPostgresqlTagRepository.save(
            DataTag(
                name = "ТФП",
                profiles = listOf(),
                isPhotographerTag = true,
                isModelTag = true,
                isVisagistTag = true,
            )
        )

        val modelPhotographerServices = modelPhotographerTagNames.map { name ->
            DataService(
                name = name,
                isModelService = true,
                isPhotographerService = true
            )
        }
        val visagistServices = visagistTagNames.map { name ->
            DataService(
                name = name,
                isVisagistService = true,
            )
        }
        springDataPostgresqlServiceRepository.saveAll(modelPhotographerServices)
        springDataPostgresqlServiceRepository.saveAll(visagistServices)

        val photosessionTagNames = listOf(
            "Свадебная съемка",
            "Студийная съемка",
            "Ню",
            "Семейная съемка",
            "Репортажная съемка",
            "Выездная фотосъемка",
        )
        val photosessionTags = photosessionTagNames.map { name ->
            DataPhotosessionTag(
                name = name,
                photosessions = listOf(),
            )
        }
        springPhotosessionTagRepository.saveAll(photosessionTags)

        val user1 = springDataPostgresqlUserRepository.save(
            BaseUser(
                id = "test_user_1",
                email = "test@test.com",
                name = "Test User",
                activationCode = "123456",
                password = passwordEncoder.encode("123456"),
                isActivatedUser = true,
                chatAccessToken = null,
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
                chatAccessToken = null,
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
                    ),
                    chatUrl = null,
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