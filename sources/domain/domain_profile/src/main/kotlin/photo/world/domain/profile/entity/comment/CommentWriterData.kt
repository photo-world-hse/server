package photo.world.domain.profile.entity.comment

import photo.world.common.profile.ProfileType

data class CommentWriterData(
    val name: String,
    val email: String,
    val avatarUrl: String?,
)