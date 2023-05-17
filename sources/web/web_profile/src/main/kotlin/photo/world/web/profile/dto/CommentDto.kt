package photo.world.web.profile.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CommentDto(
    @JsonProperty("writer_name")
    val writerName: String,
    @JsonProperty("writer_avatar")
    val writerAvatar: String?,
    val grade: Int,
    val date: Long,
    val comment: String,
    val photos: List<String>,
)
