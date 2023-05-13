package photo.world.domain.profile.service

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.Album

interface AlbumService {

    fun addAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        photos: List<String>,
        isPrivate: Boolean,
    ): Album

    fun getAlbums(accountEmail: String, profileType: ProfileType): List<Album>

    fun getAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
    ) : Album

    fun deleteAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
    )

    fun deletePhotosFromAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        photos: List<String>,
    )

    fun addPhotosToAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        photos: List<String>,
    )

    fun changeAlbumName(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        newAlbumName: String,
    )
}