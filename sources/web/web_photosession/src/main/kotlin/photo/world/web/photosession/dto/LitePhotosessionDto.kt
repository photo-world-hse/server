package photo.world.web.photosession.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class LitePhotosessionDto(
    val id: String,
    val name: String,
    val address: String,
    @JsonProperty("start_time")
    val startTime: Long,
    @JsonProperty("end_time")
    val endTime: Long,
    @JsonProperty("participants_avatars")
    val participants: List<LiteProfileDto>,
    val photosessionStatus: String?,
    @JsonProperty("chat_url")
    val chatUrl: String?,
)