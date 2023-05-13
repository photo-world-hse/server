package photo.world.domain.profile.entity.profile

import photo.world.domain.profile.entity.Album
import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.entity.comment.Comment
import photo.world.domain.profile.service.data.ProfileData

class PhotographerProfile(
    avatarUrl: String?,
    tags: List<Tag>,
    services: List<Service<*>>,
    photos: List<String>,
    albums: List<Album>,
    aboutMe: String,
    workExperience: Int,
    additionalInfo: String,
    comments: List<Comment>,
) : Profile(
    avatarUrl,
    tags,
    photos,
    albums,
    services,
    aboutMe,
    workExperience,
    additionalInfo,
    comments,
) {

    companion object {

        fun createNewProfile(
            profileData: ProfileData.PhotographerData,
        ): PhotographerProfile =
            PhotographerProfile(
                avatarUrl = profileData.avatarUrl,
                tags = profileData.tags,
                services = profileData.services,
                photos = profileData.photos,
                albums = listOf(),
                aboutMe = profileData.aboutMe,
                workExperience = profileData.workExperience,
                additionalInfo = profileData.additionalInfo,
                comments = listOf(),
            )
    }
}