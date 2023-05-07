package photo.world.web.profile.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class AlbumDto(
    val name: String,
    @JsonProperty("first_image_url")
    val firstImageUrl: String?,
    @JsonProperty("photo_number")
    val photoNumber: Int,
)
