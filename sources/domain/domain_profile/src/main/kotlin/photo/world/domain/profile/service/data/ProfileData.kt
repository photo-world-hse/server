package photo.world.domain.profile.service.data

import photo.world.domain.profile.entity.Service
import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.entity.profile.ModelProfile

sealed interface ProfileData {

    val avatarUrl: String?
    val tags: List<Tag>
    val services: List<Service<*>>
    val photos: List<String>
    var aboutMe: String
    var workExperience: Int
    var additionalInfo: String

    data class PhotographerData(
        override var avatarUrl: String?,
        override var tags: List<Tag>,
        override var services: List<Service<*>>,
        override var photos: List<String>,
        override var aboutMe: String,
        override var workExperience: Int,
        override var additionalInfo: String,
    ) : ProfileData

    data class VisagistData(
        override var avatarUrl: String?,
        override var tags: List<Tag>,
        override var services: List<Service<*>>,
        override var photos: List<String>,
        override var aboutMe: String,
        override var workExperience: Int,
        override var additionalInfo: String,
    ) : ProfileData

    data class ModelData(
        override var avatarUrl: String?,
        override var tags: List<Tag>,
        override var services: List<Service<*>>,
        override var photos: List<String>,
        override var aboutMe: String,
        override var workExperience: Int,
        override var additionalInfo: String,
        val modelParams: ModelProfile.Params,
    ) : ProfileData
}