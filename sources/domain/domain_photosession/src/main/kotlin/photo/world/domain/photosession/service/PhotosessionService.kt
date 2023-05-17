package photo.world.domain.photosession.service

import photo.world.common.profile.ProfileType
import photo.world.domain.photosession.entity.*

interface PhotosessionService {

    fun createPhotosession(
        email: String,
        profileType: ProfileType,
        photosessionData: PhotosessionData,
    ): String

    fun invite(
        organizerEmail: String,
        photosessionId: String,
        email: String,
        profileType: ProfileType,
    )

    fun inviteAll(
        organizerEmail: String,
        photosessionId: String,
        profiles: List<Pair<String, ProfileType>>,
    )

    fun cancelInvitationBy(
        photosessionId: String,
        email: String,
        profileType: ProfileType,
    )

    fun acceptInvitationBy(
        photosessionId: String,
        email: String,
        profileType: ProfileType,
    )

    fun removeParticipant(
        organizerEmail: String,
        photosessionId: String,
        email: String,
        profileType: ProfileType,
    )

    fun addPhotos(
        organizerEmail: String,
        photosessionId: String,
        photos: List<String>,
    )

    fun removePhotos(
        organizerEmail: String,
        photosessionId: String,
        photos: List<String>,
    )

    fun setTags(
        organizerEmail: String,
        photosessionId: String,
        tags: List<String>,
    )

    fun finishPhotosession(
        organizerEmail: String,
        photosessionId: String,
    )

    fun changePhotosessionData(
        organizerEmail: String,
        photosessionId: String,
        newPhotosessionData: PhotosessionData,
        newPhotos: List<String>,
        newTags: List<String>,
    ): Photosession

    fun getPhotosessionsForUser(email: String, photosessionType: PhotosessionType): List<Pair<String, Photosession>>

    fun getPhotosessionById(photosessionId: String): Photosession

    fun getReadyParticipants(photosessionId: String): List<PhotosessionProfile>
    fun addResultPhotos(
        email: String,
        photosessionId: String,
        photos: List<String>,
    )

    fun getResultPhotos(email: String, photosessionId: String): List<String>
}