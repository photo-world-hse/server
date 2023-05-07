package photo.world.infrastructure_images

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import photo.world.domain.images.ImageService

@Configuration
class ImageConfiguration {

    @Bean
    fun imageService(amazonS3Service: AmazonS3Service): ImageService = amazonS3Service
}