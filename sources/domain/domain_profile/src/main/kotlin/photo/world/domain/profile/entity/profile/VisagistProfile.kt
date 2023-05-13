package photo.world.domain.profile.entity.profile

import photo.world.domain.profile.entity.Album
import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.service.data.ProfileData

class VisagistProfile(
    avatarUrl: String?,
    tags: List<Tag>,
    aboutMe: String,
    workExperience: Int,
    additionalInfo: String,
    photos: List<String>,
    albums: List<Album>,
    services: List<Service<*>>,
    commentNumber: Int = 20,
) : Profile(
    avatarUrl,
    tags,
    photos,
    albums,
    services,
    aboutMe,
    workExperience,
    additionalInfo,
    commentNumber,
) {

    companion object {

        fun createNewProfile(
            profileData: ProfileData.VisagistData,
        ): VisagistProfile =
            VisagistProfile(
                avatarUrl = profileData.avatarUrl,
                tags = profileData.tags,
                services = profileData.services,
                photos = profileData.photos,
                albums = listOf(),
                aboutMe = profileData.aboutMe,
                workExperience = profileData.workExperience,
                additionalInfo = profileData.additionalInfo,
            )
    }
}