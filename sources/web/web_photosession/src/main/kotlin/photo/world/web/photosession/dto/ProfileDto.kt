package photo.world.web.photosession.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ProfileDto(
    val name: String,
    @JsonProperty("avatar_url")
    val avatarUrl: String?,
    @JsonProperty("profile_type")
    val profileType: String,
    val rating: Float,
    @JsonProperty("comments_number")
    val commentsNumber: Int,
    @JsonProperty("invite_status")
    val inviteStatus: String,
)