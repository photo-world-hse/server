package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangeAvatarUrlRequestDto(
    @JsonProperty("avatar_url")
    val avatarUrl: String,
)
