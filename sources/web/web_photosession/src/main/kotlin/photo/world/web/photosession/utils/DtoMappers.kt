package photo.world.web.photosession.utils

import photo.world.domain.photosession.entity.Photosession
import photo.world.domain.photosession.entity.PhotosessionProfile
import photo.world.web.photosession.dto.LiteProfileDto
import photo.world.web.photosession.dto.LitePhotosessionDto
import photo.world.web.photosession.dto.ProfileDto
import photo.world.web.photosession.dto.response.GetPhotosessionResponseDto

internal fun Photosession.toLitePhotosessionDto(photosessionId: String) =
    LitePhotosessionDto(
        id = photosessionId,
        name = photosessionData.name,
        address = photosessionData.address,
        startTime = photosessionData.startDateAndTime.time,
        endTime = getEndDate().time,
        participants = participants.map { it.toLiteProfileDto() },
        photosessionStatus = getPhotosessionStatus().name,
    )

internal fun Photosession.toPhotosessionDto(photosessionId: String) =
    GetPhotosessionResponseDto(
        id = photosessionId,
        name = photosessionData.name,
        description = photosessionData.name,
        address = photosessionData.name,
        startDateAndTime = photosessionData.startDateAndTime.time,
        endDateAndTime = getEndDate().time,
        organizer = organizer.toProfileDto(),
        participants = participants.map { it.toProfileDto() },
        photos = photos,
        resultPhotos = resultPhotos,
    )

internal fun PhotosessionProfile.toLiteProfileDto(): LiteProfileDto =
    LiteProfileDto(
        name = name,
        avatarUrl = avatarUrl,
    )

internal fun PhotosessionProfile.toProfileDto(): ProfileDto =
    ProfileDto(
        name = name,
        email = email,
        avatarUrl = avatarUrl,
        profileType = profileType.name,
        rating = rating,
        commentsNumber = commentsNumber,
        inviteStatus = inviteStatus.name,
    )