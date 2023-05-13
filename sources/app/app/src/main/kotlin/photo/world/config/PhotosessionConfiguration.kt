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

@Configuration
class PhotosessionConfiguration {

    @Bean
    fun photosessionService(
        photosessionRepository: PhotosessionRepository,
        photosessionProfileRepository: PhotosessionProfileRepository,
    ): PhotosessionService =
        DomainPhotosessionService(
            photosessionRepository,
            photosessionProfileRepository,
        )

    @Bean
    fun photosessionTagsService(
        photosessionTagsRepository: PhotosessionTagsRepository
    ): PhotosessionTagsService =
        DomainPhotosessionTagsService(photosessionTagsRepository)
}