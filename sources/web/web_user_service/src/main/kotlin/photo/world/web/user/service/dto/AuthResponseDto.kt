package photo.world.web.user.service.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class AuthResponseDto(
    @JsonProperty("session_token")
    val sessionToken: String,
    @JsonProperty("chat_access_token")
    val chatAccessToken: String,
    @JsonProperty("chat_user_id")
    val chatUserId: String,
    @JsonProperty("chat_app_id")
    val chatAppId: String,
    val username: String,
)
