package photo.world.domain.profile.ext

import photo.world.domain.profile.entity.Album
import photo.world.domain.profile.entity.profile.*

internal fun List<Album>.hasAlbum(name: String): Boolean = any { it.name == name }

internal fun List<Profile>.getProfileIndex(profileType: ProfileType) =
    indexOfFirst { profile ->
        when(profileType) {
            ProfileType.MODEL -> profile is ModelProfile
            ProfileType.PHOTOGRAPHER -> profile is PhotographerProfile
            ProfileType.VISAGIST -> profile is VisagistProfile
        }
    }