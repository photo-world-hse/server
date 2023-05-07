package photo.world.domain.profile.service.impl

import photo.world.domain.profile.entity.Album
import photo.world.domain.profile.entity.profile.Profile
import photo.world.domain.profile.entity.profile.ProfileType
import photo.world.domain.profile.repository.AccountRepository
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.service.AlbumService

class DomainAlbumService(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository,
) : AlbumService {

    override fun addAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        photos: List<String>,
    ): Album {
        val profile = getProfile(accountEmail, profileType)
        val newAlbum = profile.createAlbum(albumName, photos)
        profileRepository.update(accountEmail, profile)
        return newAlbum
    }

    override fun getAlbums(accountEmail: String, profileType: ProfileType): List<Album> {
        val profile = getProfile(accountEmail, profileType)
        return profile.albums
    }

    override fun getAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
    ): Album {
        val profile = getProfile(accountEmail, profileType)
        return profile.getAlbum(albumName)
    }

    override fun deleteAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
    ) {
        val profile = getProfile(accountEmail, profileType)
        profile.deleteAlbum(albumName)
        profileRepository.update(accountEmail, profile)
    }

    override fun deletePhotosFromAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        photos: List<String>
    ) {
        val profile = getProfile(accountEmail, profileType)
        profile.deletePhotosFromAlbum(albumName, photos)
        profileRepository.update(accountEmail, profile)
    }

    override fun addPhotosToAlbum(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        photos: List<String>
    ) {
        val profile = getProfile(accountEmail, profileType)
        profile.addPhotosToAlbum(albumName, photos)
        profileRepository.update(accountEmail, profile)
    }

    override fun changeAlbumName(
        accountEmail: String,
        profileType: ProfileType,
        albumName: String,
        newAlbumName: String
    ) {
        val profile = getProfile(accountEmail, profileType)
        profile.changeAlbumName(albumName, newAlbumName)
        profileRepository.update(accountEmail, profile)
    }

    private fun getProfile(accountEmail: String, profileType: ProfileType): Profile {
        val account = accountRepository.getAccount(accountEmail)
        return account.getProfile(profileType)
    }
}