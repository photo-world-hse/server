package photo.world.web.profile.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

internal data class GetChatUrlResponseDto(
    @JsonProperty("chat_url")
    val chatUrl: String,
)
