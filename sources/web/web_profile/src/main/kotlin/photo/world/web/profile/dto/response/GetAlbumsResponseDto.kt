package photo.world.web.profile.dto.response

import photo.world.web.profile.dto.AlbumDto

data class GetAlbumsResponseDto(
    val albums: List<AlbumDto>
)
