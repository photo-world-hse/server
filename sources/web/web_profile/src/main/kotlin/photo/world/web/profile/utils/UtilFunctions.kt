package photo.world.web.profile.utils

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.Album
import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.profile.*
import photo.world.web.profile.WebProfileConstants.ModelPathType
import photo.world.web.profile.WebProfileConstants.PhotographerPathType
import photo.world.web.profile.WebProfileConstants.VisagistPathType
import photo.world.web.profile.dto.AlbumDto
import photo.world.web.profile.dto.ProfileDto
import photo.world.web.profile.dto.ServiceDto

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
                rating = profile.rating,
                avatarUrl = profile.avatarUrl,
            )
        }

internal fun Service<*>.toServiceDto() =
    when (val cost = cost) {
        is Int -> ServiceDto(
            name = name,
            startPrice = cost,
            endPrice = null,
            payType = payType.name,
        )
        is IntRange -> ServiceDto(
            name = name,
            startPrice = cost.first,
            endPrice = cost.last,
            payType = payType.name,
        )
        null -> ServiceDto(
            name = name,
            startPrice = null,
            endPrice = null,
            payType = payType.name,
        )
        else -> error("incorrect cost type")
    }

internal fun getProfileTypeByName(profileTypeName: String): ProfileType {
    return when (profileTypeName) {
        ModelPathType -> ProfileType.MODEL
        PhotographerPathType -> ProfileType.PHOTOGRAPHER
        VisagistPathType -> ProfileType.VISAGIST
        else -> error("incorrect profile type")
    }
}

internal fun Album.toDto(): AlbumDto =
    AlbumDto(
        name = name,
        firstImageUrl = photos.firstOrNull(),
        photoNumber = photos.size,
        isPrivate = isPrivate,
    )