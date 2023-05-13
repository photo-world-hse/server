package photo.world.domain.profile.service

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.service.data.CommentData

interface CommentService {

    fun addComment(
        commentData: CommentData,
        userEmail: String,
        profileType: ProfileType,
    )
}