package photo.world.web.photosession.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.common.profile.ProfileType

data class InviteRequestDto(
    val email: String,
    @JsonProperty("profile_type")
    val profileType: ProfileType,
)
