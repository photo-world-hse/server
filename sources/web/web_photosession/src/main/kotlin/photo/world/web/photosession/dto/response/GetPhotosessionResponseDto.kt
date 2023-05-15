package photo.world.web.photosession.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.web.photosession.dto.ProfileDto

data class GetPhotosessionResponseDto(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    @JsonProperty("start_date_and_time")
    val startDateAndTime: Long,
    @JsonProperty("end_date_and_time")
    val endDateAndTime: Long,
    val duration: Float,
    val organizer: ProfileDto,
    val participants: List<ProfileDto>,
    val photos: List<String>,
    @JsonProperty("result_photos")
    val resultPhotos: List<String>,
    val tags: List<String>,
)
