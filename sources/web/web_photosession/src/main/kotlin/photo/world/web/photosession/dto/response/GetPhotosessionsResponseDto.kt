package photo.world.web.photosession.dto.response

import photo.world.web.photosession.dto.LitePhotosessionDto

data class GetPhotosessionsResponseDto(
    val photosessions: List<LitePhotosessionDto>
)
