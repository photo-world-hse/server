package photo.world.data.user.ext

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.profile.*

internal fun Profile.getProfileType(): ProfileType =
    when (this) {
        is ModelProfile -> ProfileType.MODEL
        is PhotographerProfile -> ProfileType.PHOTOGRAPHER
        is VisagistProfile -> ProfileType.VISAGIST
        else -> error("Profile has incorrect type")
    }