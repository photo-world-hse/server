package photo.world.data.user.utils

import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.profile.*
import photo.world.data.user.entity.user.BaseUser
import photo.world.data.user.ext.getProfileType
import photo.world.domain.errors.notFound
import photo.world.domain.profile.entity.*
import photo.world.domain.profile.entity.comment.Comment
import photo.world.domain.profile.entity.comment.CommentWriterData
import photo.world.domain.profile.entity.profile.*

internal fun Profile.newProfileToData(
    user: BaseUser,
    allTags: List<DataTag>,
): DataProfile {
    val profileType = getProfileType()
    return DataProfile(
        user = user,
        profileType = profileType,
        avatarUrl = avatarUrl,
        photos = photos.toDataPhotos(),
        albums = albums.map {
            DataAlbum(
                name = it.name,
                photos = it.photos.toDataPhotos(),
                isPrivate = it.isPrivate,
            )
        },
        aboutMe = aboutMe,
        workExperience = workExperience,
        additionalInfo = additionalInfo,
        comments = listOf(),
    ).apply {
        tags = allTags.filter { dataTag ->
            this@newProfileToData.tags.any { it.name == dataTag.name }
        }
        if (this@newProfileToData is ModelProfile) {
            height = modelParams.height
            hairColor = modelParams.hairColor
            eyeColor = modelParams.eyeColor
            bust = modelParams.bust
            hipGirth = modelParams.hipGirth
            waistCircumference = modelParams.waistCircumference
        }
    }
}

internal fun DataProfile.updateProfileData(
    profile: Profile,
    allTags: List<DataTag>,
): DataProfile =
    DataProfile(
        id = id,
        user = user,
        profileType = profile.getProfileType(),
        avatarUrl = profile.avatarUrl,
        photos = profile.photos.toDataPhotos(),
        albums = profile.albums.map {
            DataAlbum(
                name = it.name,
                photos = it.photos.toDataPhotos(),
                isPrivate = it.isPrivate,
            )
        },
        aboutMe = profile.aboutMe,
        workExperience = profile.workExperience,
        additionalInfo = profile.additionalInfo,
        comments = profile.comments.map { comment ->
            DataComment(
                writerName = comment.writer.name,
                writerEmail = comment.writer.email,
                writerAvatar = comment.writer.avatarUrl,
                date = comment.date,
                grade = comment.grade,
                text = comment.text,
                photos = comment.photos.map { DataPhoto(url = it) },
                isAnonymous = comment.isAnonymous,
            )
        }
    ).apply {
        tags = allTags.filter { dataTag ->
            profile.tags.any { it.name == dataTag.name }
        }
        if (profile is ModelProfile) {
            height = profile.modelParams.height
            hairColor = profile.modelParams.hairColor
            eyeColor = profile.modelParams.eyeColor
            bust = profile.modelParams.bust
            hipGirth = profile.modelParams.hipGirth
            waistCircumference = profile.modelParams.waistCircumference
        }
    }

internal fun DataProfile.toProfile(
    profileServiceRelationships: List<ProfileServiceRelationship> = listOf(),
): Profile =
    when (this.profileType) {
        ProfileType.MODEL ->
            ModelProfile(
                avatarUrl = avatarUrl,
                tags = tags.map { Tag(name = it.name) },
                photos = photos.map { it.url },
                albums = albums.map { dataAlbum ->
                    Album(
                        name = dataAlbum.name,
                        photos = dataAlbum.photos.map { it.url }.toMutableList(),
                        isPrivate = dataAlbum.isPrivate,
                    )
                },
                aboutMe = aboutMe,
                workExperience = workExperience,
                additionalInfo = additionalInfo,
                modelParams = ModelProfile.Params(
                    height = height ?: error("ModelProfile does not have height"),
                    hairColor = hairColor ?: error("ModelProfile does not have hairColor"),
                    eyeColor = eyeColor ?: error("ModelProfile does not have eyeColor"),
                    bust = bust ?: error("ModelProfile does not have bust"),
                    hipGirth = hipGirth ?: error("ModelProfile does not have hipGirth"),
                    waistCircumference = waistCircumference ?: error("ModelProfile does not have waistCircumference"),
                ),
                services = profileServiceRelationships.toServices(),
                comments = comments.map { it.toComment() },
            )

        ProfileType.PHOTOGRAPHER ->
            PhotographerProfile(
                avatarUrl = avatarUrl,
                tags = tags.map { Tag(name = it.name) },
                photos = photos.map { it.url },
                albums = albums.map { dataAlbum ->
                    Album(
                        name = dataAlbum.name,
                        photos = dataAlbum.photos.map { it.url }.toMutableList(),
                        isPrivate = dataAlbum.isPrivate,
                    )
                },
                aboutMe = aboutMe,
                workExperience = workExperience,
                additionalInfo = additionalInfo,
                services = profileServiceRelationships.toServices(),
                comments = comments.map { it.toComment() },
            )

        ProfileType.VISAGIST ->
            VisagistProfile(
                avatarUrl = avatarUrl,
                tags = tags.map { Tag(name = it.name) },
                photos = photos.map { it.url },
                albums = albums.map { dataAlbum ->
                    Album(
                        name = dataAlbum.name,
                        photos = dataAlbum.photos.map { it.url }.toMutableList(),
                        isPrivate = dataAlbum.isPrivate,
                    )
                },
                aboutMe = aboutMe,
                workExperience = workExperience,
                additionalInfo = additionalInfo,
                services = profileServiceRelationships.toServices(),
                comments = comments.map { it.toComment() },
            )
    }

internal fun BaseUser.toAccount(profiles: List<Profile>) =
    Account(
        name = name,
        email = email,
        telephone = telephone,
        profiles = profiles,
        chatAccountId = id,
    )

internal fun BaseUser.updateUserData(account: Account) =
    copy(
        email = account.email,
        name = account.name,
        telephone = account.telephone,
    )

internal fun Service<*>.toProfileServiceRelationship(
    dataProfile: DataProfile,
    dataServices: List<DataService>,
): ProfileServiceRelationship {
    val dataService = dataServices.find { it.name == this.name }
        ?: notFound<DataService>("name: ${this.name}")
    return when (val cost = cost) {
        is Int -> ProfileServiceRelationship(
            payType = payType,
            startCost = cost,
            endCost = null,
            profile = dataProfile,
            service = dataService,
        )
        is IntRange -> ProfileServiceRelationship(
            payType = payType,
            startCost = cost.first,
            endCost = cost.last,
            profile = dataProfile,
            service = dataService,
        )
        null -> ProfileServiceRelationship(
            payType = payType,
            startCost = null,
            endCost = null,
            profile = dataProfile,
            service = dataService,
        )
        else -> error("Incorrect type of cost")
    }
}

internal fun List<ProfileServiceRelationship>.toServices(): List<Service<*>> =
    map { profileService ->
        if (profileService.startCost == null) {
            Service(
                name = profileService.service.name,
                cost = null,
                payType = profileService.payType,
            )
        }
        else if (profileService.endCost == null) {
            Service(
                name = profileService.service.name,
                cost = profileService.startCost,
                payType = profileService.payType,
            )
        } else {
            Service(
                name = profileService.service.name,
                cost = profileService.startCost..profileService.endCost,
                payType = profileService.payType,
            )
        }
    }
        .sortedBy(Service<*>::name)

internal fun DataComment.toComment(): Comment =
    Comment(
        writer = CommentWriterData(
            email = writerEmail,
            name = writerName,
            avatarUrl = writerAvatar,
        ),
        date = date,
        text = text,
        photos = photos.map(DataPhoto::url),
        isAnonymous = isAnonymous,
        grade = grade,
    )

private fun List<String>.toDataPhotos() = map { DataPhoto(url = it) }