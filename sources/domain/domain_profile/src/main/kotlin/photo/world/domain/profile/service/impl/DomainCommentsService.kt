package photo.world.domain.profile.service.impl

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.comment.Comment
import photo.world.domain.profile.repository.AccountRepository
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.service.CommentService
import photo.world.domain.profile.service.data.CommentData

class DomainCommentsService(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository,
) : CommentService {

    override fun addComment(
        commentData: CommentData,
        userEmail: String,
        profileType: ProfileType,
    ) {
        val writerAccount = accountRepository.getAccount(commentData.writerEmail)
        val writerProfile = accountRepository.getProfile(commentData.writerEmail, commentData.profileType)
        val profile = profileRepository.getProfile(userEmail, profileType)
        profile.addComment(
            writerEmail = commentData.writerEmail,
            writerName = writerAccount.name,
            avatarUrl = writerProfile.avatarUrl,
            commentText = commentData.commentText,
            grade = commentData.grade,
            photos = commentData.photos,
            isAnonymous = commentData.isAnonymous,
        )
        profileRepository.update(userEmail, profile)
    }
}