package photo.world.infrastructure.sendbird.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

internal data class CreateChatDto(
    @JsonProperty("name")
    val chatName: String,
    @JsonProperty("channel_url")
    val channelUrl: String,
    @JsonProperty("user_ids")
    val userIds: List<String>,
    val users: List<String> = listOf(),
    @JsonProperty("is_public")
    val isPublic: Boolean = true,
)