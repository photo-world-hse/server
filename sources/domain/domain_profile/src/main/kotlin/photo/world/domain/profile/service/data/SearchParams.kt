package photo.world.domain.profile.service.data

import photo.world.domain.profile.entity.profile.ProfileType

data class SearchParams(
    val name: String,
    val tags: List<String>,
    val services: List<String>,
    val startWorkExperience: Int,
    val endWorkExperience: Int,
    val profileType: ProfileType,
)