package photo.world.web.profile.utils

import photo.world.domain.profile.entity.profile.*
import photo.world.web.profile.WebProfileConstants.ModelPathType
import photo.world.web.profile.WebProfileConstants.PhotographerPathType
import photo.world.web.profile.WebProfileConstants.VisagistPathType
import photo.world.web.profile.dto.ProfileDto

internal fun List<Profile>.toResponseMap() =
    groupBy { profile ->
        val keyName = when (profile) {
            is ModelProfile -> ModelPathType
            is PhotographerProfile -> PhotographerPathType
            is VisagistProfile -> VisagistPathType
            else -> error("incorrect profile type")
        }
        keyName
    }
        .mapValues { (_, value) ->
            val profile = value[0]
            ProfileDto(
                rating = 5.0f, // TODO: change this value by real rating
                avatarUrl = profile.avatarUrl,
            )
        }

internal fun getProfileTypeByName(profileTypeName: String): ProfileType {
    return when (profileTypeName) {
        ModelPathType -> ProfileType.MODEL
        PhotographerPathType -> ProfileType.PHOTOGRAPHER
        VisagistPathType -> ProfileType.VISAGIST
        else -> error("incorrect profile type")
    }
}