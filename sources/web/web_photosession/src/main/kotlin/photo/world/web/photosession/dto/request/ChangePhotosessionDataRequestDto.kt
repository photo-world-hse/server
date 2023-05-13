package photo.world.web.photosession.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class ChangePhotosessionDataRequestDto(
    val name: String,
    val description: String,
    val duration: Float,
    val address: String,
    @JsonProperty("start_date_and_time")
    val startDateAndTime: Long,
    @JsonProperty("new_photos")
    val newPhotos: List<String>,
    val tags: List<String>,
)
