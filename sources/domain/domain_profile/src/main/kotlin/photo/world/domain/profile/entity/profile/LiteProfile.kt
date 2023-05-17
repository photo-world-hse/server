package photo.world.domain.profile.entity.profile

import photo.world.domain.profile.entity.Service

data class LiteProfile(
    val name: String,
    val email: String,
    val profileType: ProfileType,
    val photos: List<String>,
    val services: List<Service<*>>,
    val tags: List<String>,
    // TODO: change when implement ratings
    val commentsNumber: Int = 20,
    val rating: Float = 5.0F,
)
