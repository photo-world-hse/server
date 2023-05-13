package photo.world.web.profile

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import photo.world.common.profile.ProfileType
import photo.world.domain.profile.service.CommentService
import photo.world.domain.profile.service.ProfileContentService
import photo.world.domain.profile.service.ProfileService
import photo.world.domain.profile.service.data.CommentData
import photo.world.web.profile.dto.request.comment.AddCommentRequestDto
import photo.world.web.profile.dto.response.comment.GetCommentsResponseDto
import photo.world.web.profile.utils.getProfileTypeByName
import photo.world.web.profile.utils.toDto

@RestController
@RequestMapping("/api/v1/profiles")
class CommentController(
    private val commentService: CommentService,
    private val profileContentService: ProfileContentService,
) {

    @GetMapping("/{profileName}/comments")
    fun getComments(
        @PathVariable profileName: String,
        authentication: Authentication,
    ): ResponseEntity<GetCommentsResponseDto> {
        val profileType = getProfileTypeByName(profileName)
        val profile = profileContentService.getProfile(authentication.name, profileType)
        return ResponseEntity.ok(
            GetCommentsResponseDto(
                comments = profile.comments.map { it.toDto() },
                averageGrade = profile.rating,
                commentNumber = profile.commentNumber,
            )
        )
    }

    @PutMapping("{email}/{profileName}/comments")
    fun addComment(
        @PathVariable profileName: String,
        @PathVariable email: String,
        @RequestBody request: AddCommentRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        commentService.addComment(
            commentData = CommentData(
                writerEmail = authentication.name,
                profileType = ProfileType.valueOf(request.writerProfileType),
                commentText = request.text,
                grade = request.grade,
                isAnonymous = request.isAnonymous,
                photos = request.photos,
            ),
            profileType = getProfileTypeByName(profileName),
            userEmail = email
        )
        return ResponseEntity.ok().build()
    }
}