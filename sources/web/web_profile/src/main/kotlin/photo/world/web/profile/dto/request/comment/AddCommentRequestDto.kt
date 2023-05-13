package photo.world.web.profile.dto.request.comment

import com.fasterxml.jackson.annotation.JsonProperty

data class AddCommentRequestDto(
    val text: String,
    val grade: Int,
    @JsonProperty("is_anonymous")
    val isAnonymous: Boolean,
    val photos: List<String>,
    @JsonProperty("writer_profile_type")
    val writerProfileType: String,
)
