package photo.world.web.photosession.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RemoveParticipantRequestDto(
    val email: String,
    @JsonProperty("profile_type")
    val profileType: String,
)
