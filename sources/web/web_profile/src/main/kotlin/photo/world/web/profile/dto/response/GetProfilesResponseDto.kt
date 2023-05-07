package photo.world.web.profile.dto.response

import photo.world.web.profile.dto.ProfileDto

data class GetProfilesResponseDto(
    val name: String,
    val profiles: Map<String, ProfileDto>
)