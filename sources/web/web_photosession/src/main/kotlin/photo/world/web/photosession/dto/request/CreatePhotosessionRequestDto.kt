package photo.world.web.photosession.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class CreatePhotosessionRequestDto(
    val profileType: String,
    @JsonProperty("photosession_name")
    val photosessionName: String,
    val description: String,
    val duration: Float,
    val address: String,
    @JsonProperty("start_date_and_time")
    val startDateAndTime: Long,
)