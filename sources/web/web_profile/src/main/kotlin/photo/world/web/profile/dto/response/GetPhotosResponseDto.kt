package photo.world.web.profile.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class GetPhotosResponseDto(
    @JsonProperty("all_photos")
    val allPhotos: List<String>,
)
