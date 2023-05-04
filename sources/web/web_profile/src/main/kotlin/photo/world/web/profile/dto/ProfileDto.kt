package photo.world.web.profile.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ProfileDto(
    val rating: Float,
    @JsonProperty("avatar_url") val avatarUrl: String?,
)
