package photo.world.domain.photosession.entity

import photo.world.common.profile.ProfileType
import photo.world.domain.errors.DomainException
import java.util.Date

class Photosession(
    val organizer: PhotosessionProfile,
    photosessionData: PhotosessionData,
) {

    private val mutableParticipants: MutableList<PhotosessionProfile> = mutableListOf()
    val participants: List<PhotosessionProfile> = mutableParticipants

    private val mutablePhotos: MutableList<String> = mutableListOf()
    val photos: List<String> = mutablePhotos

    private val mutableResultPhotos: MutableList<String> = mutableListOf()
    val resultPhotos: List<String> = mutableResultPhotos

    private val mutableTags: MutableList<String> = mutableListOf()
    val tags: List<String> = mutableTags

    var chatUrl: String? = null
        private set

    var photosessionData: PhotosessionData = photosessionData
        private set
    var isFinished: Boolean = false
        private set

    fun edit(newPhotosessionData: PhotosessionData) {
        photosessionData = newPhotosessionData
    }

    fun invite(
        profileData: ProfileData,
        notificationSender: ((String) -> Unit)? = null,
    ) {
        val profile = profileData.toPhotosessionProfile(InviteStatus.PENDING)
        mutableParticipants.add(profile)
        notificationSender?.invoke(profile.email)
    }

    fun inviteAll(
        profilesData: List<ProfileData>,
        notificationSender: NotificationSender? = null,
    ) {
        val profiles = profilesData.map { profileData ->
            profileData.toPhotosessionProfile(InviteStatus.PENDING)
        }
        mutableParticipants.addAll(profiles)
        profiles.forEach { notificationSender?.sendNotification(it.email) }
    }

    fun cancelInvitationBy(
        email: String,
        profileType: ProfileType,
        notificationSender: NotificationSender? = null,
    ) {
        val profile = findProfileByEmailAndProfileType(email, profileType)
        mutableParticipants.remove(profile)
        val newProfile = profile.copy(inviteStatus = InviteStatus.CANCELED)
        mutableParticipants.add(newProfile)
        notificationSender?.sendNotification(organizer.email)
    }

    fun acceptInvitationBy(
        email: String,
        profileType: ProfileType,
        notificationSender: NotificationSender? = null,
        addUserToChatAction: (String) -> Unit,
    ) {
        val profile = findProfileByEmailAndProfileType(email, profileType)
        mutableParticipants.remove(profile)
        val containsAnotherProfileForUser = participants.any { it.email == email }
        if (!containsAnotherProfileForUser) {
            chatUrl?.let { addUserToChatAction(it) }
        }
        val newProfile = profile.copy(inviteStatus = InviteStatus.READY)
        mutableParticipants.add(newProfile)
        notificationSender?.sendNotification(organizer.email)
    }

    fun removeParticipant(email: String, profileType: ProfileType) {
        val profile = findProfileByEmailAndProfileType(email, profileType)
        mutableParticipants.remove(profile)
    }

    fun addPhotos(photos: List<String>) {
        mutablePhotos.addAll(photos)
    }

    fun removePhotos(photos: List<String>) {
        val nonexistentPhotos = mutableListOf<String>()
        photos.forEach { photoUrl ->
            if (mutablePhotos.contains(photoUrl)) {
                mutablePhotos.remove(photoUrl)
            } else {
                nonexistentPhotos.add(photoUrl)
            }
        }
        if (nonexistentPhotos.isNotEmpty())
            throw DomainException(
                "Photosession does not exists: ${nonexistentPhotos.joinToString(", ")}"
            )
    }

    fun setTags(tags: List<String>) {
        mutableTags.clear()
        mutableTags.addAll(tags)
    }

    fun changePhotosessionInfo(newPhotosessionData: PhotosessionData) {
        photosessionData = newPhotosessionData
    }

    fun finish(notificationSender: NotificationSender? = null) {
        isFinished = true
        participants.forEach { notificationSender?.sendNotification(it.email) }
    }

    fun attachChat(chatUrl: String) {
        this.chatUrl = chatUrl
    }

    fun getPhotosessionStatus(): PhotosessionStatus =
        when {
            isFinished -> PhotosessionStatus.NONE
            participants.any { it.inviteStatus == InviteStatus.CANCELED } -> PhotosessionStatus.REFUSAL
            participants.any { it.inviteStatus == InviteStatus.PENDING } -> PhotosessionStatus.EXPECTATION
            participants.all { it.inviteStatus == InviteStatus.READY } -> PhotosessionStatus.RECEPTION_COMPLETED
            else -> PhotosessionStatus.NONE
        }

    fun getEndDate(): Date {
        val durationInSeconds = (photosessionData.duration * 60 * 60).toLong()
        val endDateAndTimeInstant = photosessionData.startDateAndTime.toInstant().plusSeconds(durationInSeconds)
        return Date.from(endDateAndTimeInstant)
    }

    private fun findProfileByEmailAndProfileType(
        email: String,
        profileType: ProfileType,
    ): PhotosessionProfile {
        val profile = participants.find { it.email == email && it.profileType == profileType }
        return profile
            ?: throw DomainException(
            "Photossession does not have participant profile" +
                " with email = $email and profile type = ${profileType.name.lowercase()}"
        )
    }

    fun addResultPhotos(photos: List<String>) {
        if (isFinished) {
            mutableResultPhotos.addAll(photos)
        } else {
            throw DomainException("You can upload final photos only after the end of the photosession")
        }
    }

    companion object {

        fun createPhotosession(
            organizerData: ProfileData,
            photosessionData: PhotosessionData,
            chatUrl: String?,
        ): Photosession =
            Photosession(
                organizer = PhotosessionProfile(
                    email = organizerData.email,
                    name = organizerData.name,
                    profileType = organizerData.profileType,
                    inviteStatus = InviteStatus.READY,
                    avatarUrl = organizerData.avatarUrl,
                    commentsNumber = organizerData.commentsNumber,
                    rating = organizerData.rating,
                ),
                photosessionData = photosessionData,
            ).apply {
                this.chatUrl = chatUrl
            }

        fun createPhotosessionFromData(
            organizer: PhotosessionProfile,
            photosessionData: PhotosessionData,
            chatUrl: String?,
            participants: List<PhotosessionProfile>,
            photos: List<String>,
            resultPhotos: List<String>,
            tags: List<String>,
            isFinished: Boolean,
        ): Photosession =
            Photosession(
                organizer = organizer,
                photosessionData = photosessionData,
            ).apply {
                mutableParticipants.addAll(participants)
                mutablePhotos.addAll(photos)
                mutableResultPhotos.addAll(resultPhotos)
                mutableTags.addAll(tags)
                this.isFinished = isFinished
                this.chatUrl = chatUrl
            }
    }
}