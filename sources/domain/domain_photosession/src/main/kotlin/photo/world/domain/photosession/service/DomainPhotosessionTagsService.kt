package photo.world.domain.photosession.service

import photo.world.domain.photosession.repository.PhotosessionTagsRepository

class DomainPhotosessionTagsService(
    val tagsRepository: PhotosessionTagsRepository,
) : PhotosessionTagsService {

    override fun getAllTags(): List<String> = tagsRepository.getAllTags()
}