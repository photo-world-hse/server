package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class DeletePhotoRequestDto(
    @JsonProperty("photo_url")
    val photoUrl: String,
)