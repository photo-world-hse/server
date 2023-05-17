package photo.world.web.photosession.dto.response

import photo.world.web.photosession.dto.ProfileDto

data class GetPhotosessionResponseDto(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    val startDateAndTime: Long,
    val endDateAndTime: Long,
    val organizer: ProfileDto,
    val participants: List<ProfileDto>,
    val photos: List<String>,
    val resultPhotos: List<String>,
)
