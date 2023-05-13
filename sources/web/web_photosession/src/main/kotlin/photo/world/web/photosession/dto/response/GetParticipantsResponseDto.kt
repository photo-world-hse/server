package photo.world.web.photosession.dto.response

import photo.world.web.photosession.dto.ProfileDto

data class GetParticipantsResponseDto(
    val participants: List<ProfileDto>,
)
