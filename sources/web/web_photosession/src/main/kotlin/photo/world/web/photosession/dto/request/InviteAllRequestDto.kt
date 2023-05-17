package photo.world.web.photosession.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class InviteAllRequestDto(
    val invitations: List<InvitationDto>,
)

data class InvitationDto(
    val email: String,
    @JsonProperty("profile_type")
    val profileType: String,
)