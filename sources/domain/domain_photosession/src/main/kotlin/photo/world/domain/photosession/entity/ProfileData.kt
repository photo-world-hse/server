package photo.world.domain.photosession.entity

import photo.world.common.profile.ProfileType

data class ProfileData(
    val email: String,
    val name: String,
    val profileType: ProfileType,
    val avatarUrl: String?,
    val rating: Float,
    val commentsNumber: Int,
    val userId: String,
) {

    fun toPhotosessionProfile(inviteStatus: InviteStatus): PhotosessionProfile =
        PhotosessionProfile(
            email = email,
            name = name,
            profileType = profileType,
            avatarUrl = avatarUrl,
            rating = rating,
            commentsNumber = commentsNumber,
            inviteStatus = inviteStatus,
        )
}
