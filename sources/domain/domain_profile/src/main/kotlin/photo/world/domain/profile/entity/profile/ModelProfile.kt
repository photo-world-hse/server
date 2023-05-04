package photo.world.domain.profile.entity.profile

import photo.world.domain.profile.entity.Album
import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.service.data.ProfileData

class ModelProfile(
    avatarUrl: String?,
    tags: List<Tag>,
    aboutMe: String,
    workExperience: Int,
    additionalInfo: String,
    photos: List<String>,
    albums: List<Album>,
    services: List<Service<*>>,
    modelParams: Params,
) : Profile(
    avatarUrl,
    tags,
    photos,
    albums,
    services,
    aboutMe,
    workExperience,
    additionalInfo,
) {

    var modelParams = modelParams
        private set

    fun changeModelParams(
        modelParams: Params,
    ) {
        this.modelParams = modelParams
    }

    data class Params(
        val height: Int,
        val hairColor: String,
        val eyeColor: String,
        val bust: Int,
        val hipGirth: Int,
        val waistCircumference: Int,
    )

    companion object {

        fun createNewProfile(
            profileData: ProfileData.ModelData,
        ): ModelProfile =
            ModelProfile(
                avatarUrl = profileData.avatarUrl,
                tags = profileData.tags,
                services = profileData.services,
                photos = profileData.photos,
                albums = listOf(),
                aboutMe = profileData.aboutMe,
                workExperience = profileData.workExperience,
                additionalInfo = profileData.additionalInfo,
                modelParams = profileData.modelParams,
            )
    }
}