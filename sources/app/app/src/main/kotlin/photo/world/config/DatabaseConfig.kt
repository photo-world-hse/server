package photo.world.config

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import photo.world.data.user.entity.profile.DataService
import photo.world.data.user.entity.profile.DataTag
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlServiceRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlTagRepository

@Configuration
class DatabaseConfig {

    @Bean
    fun databaseCommandLineRunner(
        springDataPostgresqlTagRepository: SpringDataPostgresqlTagRepository,
        springDataPostgresqlServiceRepository: SpringDataPostgresqlServiceRepository,
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
    }
}