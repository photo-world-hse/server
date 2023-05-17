package photo.world.web.photosession

import jakarta.websocket.server.PathParam
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import photo.world.common.profile.ProfileType
import photo.world.domain.photosession.entity.PhotosessionData
import photo.world.domain.photosession.entity.PhotosessionType
import photo.world.domain.photosession.service.PhotosessionService
import photo.world.domain.photosession.service.PhotosessionTagsService
import photo.world.web.photosession.dto.request.*
import photo.world.web.photosession.dto.response.*
import photo.world.web.photosession.utils.toLitePhotosessionDto
import photo.world.web.photosession.utils.toPhotosessionDto
import photo.world.web.photosession.utils.toProfileDto
import java.util.*

@RestController
@RequestMapping("/api/v1/photosessions")
class PhotosessionController(
    private val photosessionService: PhotosessionService,
    private val photosessionTagsService: PhotosessionTagsService,
) {

    @GetMapping
    fun getPhotosessions(
        @PathParam("type") type: String,
        authentication: Authentication,
    ): ResponseEntity<GetPhotosessionsResponseDto> {
        val photosessionType = PhotosessionType.valueOf(type)
        val photosessions = photosessionService.getPhotosessionsForUser(
            email = authentication.name,
            photosessionType = photosessionType,
        )
        return ResponseEntity.ok(
            GetPhotosessionsResponseDto(
                photosessions = photosessions.map { (id, photosession) ->
                    photosession.toLitePhotosessionDto(id)
                },
            )
        )
    }

    @GetMapping("/{photosessionId}")
    fun getPhotosession(
        @PathVariable photosessionId: String,
    ): ResponseEntity<GetPhotosessionResponseDto> {
        val photosession = photosessionService.getPhotosessionById(photosessionId)
        return ResponseEntity.ok(photosession.toPhotosessionDto(photosessionId))
    }

    @PutMapping
    fun createPhotosession(
        @RequestBody request: CreatePhotosessionRequestDto,
        authentication: Authentication,
    ): ResponseEntity<CreatePhotosessionResponseDto> {
        val photosessionId = photosessionService.createPhotosession(
            email = authentication.name,
            profileType = ProfileType.valueOf(request.profileType),
            photosessionData = PhotosessionData(
                name = request.photosessionName,
                description = request.description,
                startDateAndTime = Date(request.startDateAndTime),
                duration = request.duration,
                address = request.address,
            )
        )
        return ResponseEntity.ok(CreatePhotosessionResponseDto(photosessionId))
    }

    @PostMapping("/{photosessionId}/data")
    fun changePhotosessionData(
        @PathVariable photosessionId: String,
        @RequestBody request: ChangePhotosessionDataRequestDto,
        authentication: Authentication,
    ): ResponseEntity<GetPhotosessionResponseDto> {
        val newPhotosession = photosessionService.changePhotosessionData(
            organizerEmail = authentication.name,
            photosessionId = photosessionId,
            newPhotos = request.newPhotos,
            newTags = request.tags,
            newPhotosessionData = PhotosessionData(
                name = request.name,
                description = request.description,
                startDateAndTime = Date(request.startDateAndTime),
                duration = request.duration,
                address = request.address,
            )
        )
        return ResponseEntity.ok(newPhotosession.toPhotosessionDto(photosessionId))
    }

    @PostMapping("/{photosessionId}/participants/invite")
    fun sendInvitationToProfile(
        @PathVariable("photosessionId") photosessionId: String,
        @RequestBody request: InviteRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        photosessionService.invite(
            organizerEmail = authentication.name,
            photosessionId = photosessionId,
            email = request.email,
            profileType = request.profileType,
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{photosessionId}/participants/inviteAll")
    fun sendInvitationToProfiles(
        @PathVariable("photosessionId") photosessionId: String,
        @RequestBody request: InviteAllRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        photosessionService.inviteAll(
            organizerEmail = authentication.name,
            photosessionId = photosessionId,
            profiles = request.invitations.map { it.email to ProfileType.valueOf(it.profileType) },
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{photosessionId}/participants/cancel/{profileType}")
    fun cancelInvitationFromPhotosession(
        @PathVariable("photosessionId") photosessionId: String,
        @PathVariable("profileType") profileType: ProfileType,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        photosessionService.cancelInvitationBy(
            photosessionId = photosessionId,
            email = authentication.name,
            profileType = profileType,
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{photosessionId}/participants/accept/{profileType}")
    fun acceptInvitationToPhotosession(
        @PathVariable("photosessionId") photosessionId: String,
        @PathVariable("profileType") profileType: ProfileType,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        photosessionService.acceptInvitationBy(
            photosessionId = photosessionId,
            email = authentication.name,
            profileType = profileType,
        )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{photosessionId}/participants")
    fun removeParticipantFromProfile(
        @PathVariable("photosessionId") photosessionId: String,
        @RequestBody request: RemoveParticipantRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        photosessionService.removeParticipant(
            organizerEmail = authentication.name,
            photosessionId = photosessionId,
            email = request.email,
            profileType = ProfileType.valueOf(request.profileType),
        )
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{photosessionId}/participants/ready")
    fun getParticipantsForComments(
        @PathVariable("photosessionId") photosessionId: String,
    ): ResponseEntity<GetParticipantsResponseDto> {
        val participants = photosessionService.getReadyParticipants(photosessionId)
        return ResponseEntity.ok(
            GetParticipantsResponseDto(participants.map { it.toProfileDto() })
        )
    }

    @GetMapping("/tags")
    fun getPhotosessionTags(): ResponseEntity<GetTagsResponseDto> {
        return ResponseEntity.ok(
            GetTagsResponseDto(tags = photosessionTagsService.getAllTags())
        )
    }
}