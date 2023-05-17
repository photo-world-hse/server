package photo.world.infrastructure.sendbird.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

internal data class AccessTokenDto(
    @JsonProperty("access_token")
    val accessToken: String,
)
