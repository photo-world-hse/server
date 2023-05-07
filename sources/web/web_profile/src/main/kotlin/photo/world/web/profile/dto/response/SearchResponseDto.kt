package photo.world.web.profile.dto.response

import photo.world.web.profile.dto.LiteProfileDto

internal data class SearchResponseDto(
    val profiles: List<LiteProfileDto>,
)