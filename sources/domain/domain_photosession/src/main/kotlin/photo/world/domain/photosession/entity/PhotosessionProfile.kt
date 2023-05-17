package photo.world.domain.photosession.entity

import photo.world.common.profile.ProfileType

data class PhotosessionProfile(
    val email: String,
    val name: String,
    val profileType: ProfileType,
    val inviteStatus: InviteStatus,
    val avatarUrl: String?,
    val rating: Float,
    val commentsNumber: Int,
)