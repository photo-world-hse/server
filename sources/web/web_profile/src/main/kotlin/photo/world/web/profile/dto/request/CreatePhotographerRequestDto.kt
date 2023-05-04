package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.web.profile.dto.ServiceDto

internal data class CreatePhotographerRequestDto(
    val tags: List<String>,
    @JsonProperty("about_me") val aboutMe: String,
    @JsonProperty("work_experience") val workExperience: Int,
    @JsonProperty("extra_info") val extraInfo: String,
    val photos: List<String>,
    @JsonProperty("avatar_url") val avatarUrl: String,
    val services: List<ServiceDto>,
)