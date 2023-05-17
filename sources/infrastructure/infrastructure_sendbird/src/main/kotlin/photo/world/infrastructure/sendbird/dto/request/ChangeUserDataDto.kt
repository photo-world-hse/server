package photo.world.infrastructure.sendbird.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

internal data class ChangeUserDataDto(
    @JsonProperty("profile_url")
    val profileUrl: String,
)
