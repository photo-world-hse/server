package photo.world.web.images.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class DeleteFileRequest(
    @JsonProperty("image_url") val imageUrl: String,
)
