package photo.world.domain.profile.entity.comment

import java.util.Date

data class Comment(
    val writer: CommentWriterData,
    val date: Date,
    val grade: Int,
    val text: String,
    val photos: List<String>,
    val isAnonymous: Boolean,
)