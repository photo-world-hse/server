package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangeAlbumNameRequestDto(
    @JsonProperty("new_name")
    val newName: String,
)
