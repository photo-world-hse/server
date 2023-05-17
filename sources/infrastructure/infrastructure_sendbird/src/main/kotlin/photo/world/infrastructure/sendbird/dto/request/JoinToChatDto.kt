package photo.world.infrastructure.sendbird.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

internal data class JoinToChatDto(
    @JsonProperty("user_id")
    val userId: String,
)