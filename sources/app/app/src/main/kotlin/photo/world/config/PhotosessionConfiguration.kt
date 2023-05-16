package photo.world.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import photo.world.domain.photosession.repository.PhotosessionProfileRepository
import photo.world.domain.photosession.repository.PhotosessionRepository
import photo.world.domain.photosession.repository.PhotosessionTagsRepository
import photo.world.domain.photosession.service.DomainPhotosessionService
import photo.world.domain.photosession.service.DomainPhotosessionTagsService
import photo.world.domain.photosession.service.PhotosessionService
import photo.world.domain.photosession.service.PhotosessionTagsService
import photo.world.infrastructure.sendbird.ChatServiceImpl

@Configuration
class PhotosessionConfiguration {

    @Bean
    fun photosessionService(
        photosessionRepository: PhotosessionRepository,
        photosessionProfileRepository: PhotosessionProfileRepository,
        chatService: ChatServiceImpl,
    ): PhotosessionService =
        DomainPhotosessionService(
            photosessionRepository,
            photosessionProfileRepository,
            chatService,
        )

    @Bean
    fun photosessionTagsService(
        photosessionTagsRepository: PhotosessionTagsRepository
    ): PhotosessionTagsService =
        DomainPhotosessionTagsService(photosessionTagsRepository)
}