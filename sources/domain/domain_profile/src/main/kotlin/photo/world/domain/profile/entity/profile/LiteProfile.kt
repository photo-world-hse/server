package photo.world.domain.profile.entity.profile

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.Service

data class LiteProfile(
    val name: String,
    val email: String,
    val profileType: ProfileType,
    val photos: List<String>,
    val services: List<Service<*>>,
    val tags: List<String>,
    val commentsNumber: Int,
    val rating: Float,
)
