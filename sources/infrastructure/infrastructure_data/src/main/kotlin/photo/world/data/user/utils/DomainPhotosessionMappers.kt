package photo.world.data.user.utils

import photo.world.data.user.entity.photosession.DataPhotosession
import photo.world.data.user.entity.photosession.DataPhotosessionTag
import photo.world.data.user.entity.photosession.ParticipateRelationship
import photo.world.data.user.entity.profile.DataPhoto
import photo.world.data.user.entity.profile.DataProfile
import photo.world.domain.errors.notFound
import photo.world.domain.photosession.entity.InviteStatus
import photo.world.domain.photosession.entity.Photosession
import photo.world.domain.photosession.entity.PhotosessionData
import photo.world.domain.photosession.entity.PhotosessionProfile

internal fun DataProfile.toPhotosessionProfile(inviteStatus: InviteStatus): PhotosessionProfile {
    val profile = toProfile()
    return PhotosessionProfile(
        name = user.name,
        email = user.email,
        profileType = profileType,
        inviteStatus = inviteStatus,
        avatarUrl = avatarUrl,
        rating = profile.rating,
        commentsNumber = profile.commentNumber,
    )
}

internal fun DataPhotosession.toPhotosession(): Photosession {
    return Photosession.createPhotosessionFromData(
        organizer = organizer.toPhotosessionProfile(InviteStatus.READY),
        photosessionData = PhotosessionData(
            name = name,
            description = description,
            duration = duration,
            address = address,
            startDateAndTime = startDateAndTime,
        ),
        participants = participateRelationships.map { relationship ->
            relationship.profile.toPhotosessionProfile(relationship.inviteStatus)
        },
        tags = tags.map(DataPhotosessionTag::name),
        photos = photos.map(DataPhoto::url),
        resultPhotos = resultPhotos.map(DataPhoto::url),
        isFinished = isFinished,
    )
}

internal fun Photosession.toDataPhotosession(
    photosessionId: String,
    organizer: DataProfile,
    dataTags: List<DataPhotosessionTag>,
): DataPhotosession =
    DataPhotosession(
        id = photosessionId,
        name = photosessionData.name,
        description = photosessionData.description,
        duration = photosessionData.duration,
        address = photosessionData.address,
        startDateAndTime = photosessionData.startDateAndTime,
        organizer = organizer,
        photos = photos.map { DataPhoto(url = it) },
        resultPhotos = photos.map { DataPhoto(url = it) },
        isFinished = isFinished,
    ).apply {
        this.tags = this@toDataPhotosession.tags.map { tag ->
            dataTags.find { it.name == tag }
                ?: notFound<DataPhotosessionTag>("name: ${this.name}")
        }
    }

internal fun Photosession.toNewDataPhotosession(
    organizer: DataProfile,
): DataPhotosession =
    DataPhotosession(
        name = photosessionData.name,
        description = photosessionData.description,
        duration = photosessionData.duration,
        address = photosessionData.address,
        startDateAndTime = photosessionData.startDateAndTime,
        organizer = organizer,
        photos = photos.map { DataPhoto(url = it) },
        resultPhotos = photos.map { DataPhoto(url = it) },
        isFinished = isFinished,
    )