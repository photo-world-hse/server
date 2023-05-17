package photo.world.web.profile.dto.response.comment

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.web.profile.dto.CommentDto

data class GetCommentsResponseDto(
    @JsonProperty("comment_number")
    val commentNumber: Int,
    @JsonProperty("average_grade")
    val averageGrade: Float,
    val comments: List<CommentDto>,
)