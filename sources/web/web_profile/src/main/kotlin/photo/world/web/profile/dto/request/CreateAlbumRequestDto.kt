package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateAlbumRequestDto(
    @JsonProperty("album_name")
    val albumName: String,
    val photos: List<String>,
    @JsonProperty("is_private")
    val isPrivate: Boolean,
)
