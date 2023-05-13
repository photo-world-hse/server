package photo.world.domain.photosession.repository

interface PhotosessionTagsRepository {

    fun getAllTags(): List<String>
}