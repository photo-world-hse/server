package photo.world.web.profile.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class LiteProfileDto(
    val name: String,
    val email: String,
    val photos: List<String>,
    val services: List<ServiceDto>,
    val rating: Float,
    @JsonProperty("comments_number")
    val commentsNumber: Int,
)
