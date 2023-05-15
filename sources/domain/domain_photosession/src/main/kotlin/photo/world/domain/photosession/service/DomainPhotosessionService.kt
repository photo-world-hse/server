package photo.world.domain.photosession.service

import photo.world.common.profile.ProfileType
import photo.world.domain.errors.ForbiddenException
import photo.world.domain.photosession.entity.*
import photo.world.domain.photosession.repository.PhotosessionProfileRepository
import photo.world.domain.photosession.repository.PhotosessionRepository

class DomainPhotosessionService(
    private val photosessionRepository: PhotosessionRepository,
    private val photosessionProfileRepository: PhotosessionProfileRepository,
) : PhotosessionService {

    override fun createPhotosession(
        email: String,
        profileType: ProfileType,
        photosessionData: PhotosessionData,
    ): String {
        val profileData = photosessionProfileRepository.getProfileInfo(email, profileType)
        val photosession = Photosession.createPhotosession(
            organizerData = profileData,
            photosessionData = photosessionData,
        )
        return photosessionRepository.saveNew(photosession)
    }

    override fun invite(
        organizerEmail: String,
        photosessionId: String,
        email: String,
        profileType: ProfileType,
    ) {
        val profileData = photosessionProfileRepository.getProfileInfo(email, profileType)
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) { invite(profileData) }
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun inviteAll(
        organizerEmail: String,
        photosessionId: String,
        profiles: List<Pair<String, ProfileType>>,
    ) {
        val profilesData = profiles.map { (email, profileType) ->
            photosessionProfileRepository.getProfileInfo(email, profileType)
        }
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) { inviteAll(profilesData) }
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun cancelInvitationBy(photosessionId: String, email: String, profileType: ProfileType) {
        val photosession = photosessionRepository.getById(photosessionId)
        photosession.cancelInvitationBy(email, profileType)
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun acceptInvitationBy(photosessionId: String, email: String, profileType: ProfileType) {
        val photosession = photosessionRepository.getById(photosessionId)
        photosession.acceptInvitationBy(email, profileType)
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun removeParticipant(
        organizerEmail: String,
        photosessionId: String,
        email: String,
        profileType: ProfileType,
    ) {
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) { removeParticipant(email, profileType) }
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun addPhotos(
        organizerEmail: String,
        photosessionId: String,
        photos: List<String>,
    ) {
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) { addPhotos(photos) }
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun removePhotos(
        organizerEmail: String,
        photosessionId: String,
        photos: List<String>,
    ) {
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) { removePhotos(photos) }
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun setTags(
        organizerEmail: String,
        photosessionId: String,
        tags: List<String>,
    ) {
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) { setTags(tags) }
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun finishPhotosession(
        organizerEmail: String,
        photosessionId: String,
    ) {
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) { finish() }
        photosessionRepository.saveById(photosessionId, photosession)
    }

    override fun changePhotosessionData(
        organizerEmail: String,
        photosessionId: String,
        newPhotosessionData: PhotosessionData,
        newPhotos: List<String>,
        newTags: List<String>,
    ): Photosession {
        val photosession = photosessionRepository.getById(photosessionId)
        doActionIfOrganizer(organizerEmail, photosession) {
            changePhotosessionInfo(newPhotosessionData)
            addPhotos(newPhotos)
            setTags(newTags)
        }
        photosessionRepository.saveById(photosessionId, photosession)
        return photosession
    }

    override fun getPhotosessionsForUser(
        email: String,
        photosessionType: PhotosessionType,
    ): List<Pair<String, Photosession>> =
        when (photosessionType) {
            PhotosessionType.ACTIVE -> photosessionRepository.getAllActiveForUser(email)
            PhotosessionType.ARCHIVE -> photosessionRepository.getAllArchive(email)
            PhotosessionType.INVITE -> photosessionRepository.getAllInvitations(email)
            PhotosessionType.ALL -> photosessionRepository.getAll(email)
        }

    override fun getPhotosessionById(photosessionId: String): Photosession =
        photosessionRepository.getById(photosessionId)

    override fun getReadyParticipants(photosessionId: String): List<PhotosessionProfile> {
        val photosession = photosessionRepository.getById(photosessionId)
        return photosession.participants.filter { it.inviteStatus == InviteStatus.READY }
    }

    override fun addResultPhotos(
        email: String,
        photosessionId: String,
        photos: List<String>,
    ) {
        val photosession = photosessionRepository.getById(photosessionId)
        val isParticipant = photosession.participants.any { it.email == email }
            || photosession.organizer.email == email
        if (isParticipant) {
            photosession.addResultPhotos(photos)
            photosessionRepository.saveById(photosessionId, photosession)
        } else {
            throw ForbiddenException(
                message = "Only participants can do this action, but user $email isn't a participant",
            )
        }
    }

    private inline fun doActionIfOrganizer(
        userEmail: String,
        photosession: Photosession,
        action: Photosession.() -> Unit,
    ) {
        if (photosession.organizer.email == userEmail) {
            photosession.action()
        } else {
            throw ForbiddenException(
                message = "Only the organizer can do this action, but user $userEmail doesn't organizer",
            )
        }
    }
}