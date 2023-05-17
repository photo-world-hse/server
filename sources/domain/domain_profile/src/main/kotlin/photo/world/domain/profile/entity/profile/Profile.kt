package photo.world.domain.profile.entity.profile

import photo.world.domain.errors.DomainException
import photo.world.domain.profile.entity.Album
import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.ext.hasAlbum

abstract class Profile(
    avatarUrl: String?,
    tags: List<Tag>,
    photos: List<String>,
    albums: List<Album>,
    services: List<Service<*>>,
    aboutMe: String,
    workExperience: Int,
    additionalInfo: String,
    val rating: Float = 5.0f,
) {

    var avatarUrl: String? = avatarUrl
        private set
    var aboutMe: String = aboutMe
        private set
    var workExperience: Int = workExperience
        private set
    var additionalInfo: String = additionalInfo
        private set

    private val mutableTags = tags.toMutableList()
    private val mutablePhotos = photos.toMutableList()
    private val mutableAlbums = albums.toMutableList()
    private val mutableServices = services.toMutableList()

    val tags: List<Tag> = mutableTags
    val photos: List<String> = mutablePhotos
    val albums: List<Album> = mutableAlbums
    val services: List<Service<*>> = mutableServices

    internal fun changeAvatar(newAvatarUrl: String) {
        avatarUrl = newAvatarUrl
    }

    internal fun addPhotos(photosUrl: List<String>) {
        mutablePhotos.addAll(photosUrl)
    }

    internal fun deletePhoto(photoUrl: String) {
        if (mutablePhotos.contains(photoUrl)) {
            mutablePhotos.remove(photoUrl)
        } else {
            val album = albums.find { it.photos.contains(photoUrl) }
                ?: throw DomainException("Profile does not have image with url: $photoUrl")
            album.photos.remove(photoUrl)
        }
    }

    internal fun getAllPhotos(): List<String> =
        mutableAlbums.flatMap { it.photos }.plus(mutablePhotos).distinct()

    internal fun createAlbum(
        albumName: String,
        photos: List<String>,
        isPrivate: Boolean,
    ): Album {
        if (mutableAlbums.hasAlbum(albumName))
            throw DomainException("Album with name $albumName already exists")
        val newAlbum = Album(
            name = albumName,
            photos = photos.toMutableList(),
            isPrivate = isPrivate,
        )
        mutableAlbums.add(newAlbum)
        return newAlbum
    }

    internal fun getAlbum(albumName: String): Album {
        return albums.find { it.name == albumName }
            ?: throw DomainException("Profile does not have album with name $albumName")
    }

    internal fun deletePhotosFromAlbum(albumName: String, photos: List<String>) {
        val album = getAlbum(albumName)
        photos.forEach { photoUrl ->
            album.photos.remove(photoUrl)
            mutablePhotos.add(photoUrl)
        }
    }

    internal fun addPhotosToAlbum(albumName: String, photos: List<String>) {
        val album = getAlbum(albumName)
        album.photos.addAll(photos)
    }

    internal fun changeAlbumName(albumName: String, newAlbumName: String) {
        val album = getAlbum(albumName)
        mutableAlbums.remove(album)
        mutableAlbums.add(album.copy(name = newAlbumName))
    }

    internal fun deleteAlbum(albumName: String) {
        val album = getAlbum(albumName)
        mutablePhotos.addAll(album.photos)
        mutableAlbums.remove(album)
    }

    internal fun changeServices(newServices: List<Service<*>>) {
        mutableServices.clear()
        mutableServices.addAll(newServices)
    }

    internal fun changeTags(tags: List<String>) {
        val newTags = tags.map { tagName -> Tag(tagName) }
        mutableTags.clear()
        mutableTags.addAll(newTags)
    }

    internal fun changeProfileInfo(
        aboutMe: String,
        workExperience: Int,
        additionalInfo: String,
    ) {
        this.aboutMe = aboutMe
        this.workExperience = workExperience
        this.additionalInfo = additionalInfo
    }
}