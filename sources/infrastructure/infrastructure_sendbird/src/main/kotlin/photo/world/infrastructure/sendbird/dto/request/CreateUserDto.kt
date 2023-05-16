package photo.world.infrastructure.sendbird.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

internal data class CreateUserDto(
    @JsonProperty("user_id")
    @JvmField
    val userID: String,
    @JvmField
    val nickname: String,
    @JsonProperty("profile_url")
    @JvmField
    val profileURL: String = "",
    @JsonProperty("issue_access_token")
    @JvmField
    val issueAccessToken: Boolean = true,
)