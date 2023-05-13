package photo.world.domain.profile.service.data

import photo.world.common.profile.ProfileType

data class CommentData(
    val writerEmail: String,
    val profileType: ProfileType,
    val commentText: String,
    val grade: Int,
    val isAnonymous: Boolean,
    val photos: List<String>,
)
