package photo.world.web.profile.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.domain.profile.entity.profile.ModelProfile
import photo.world.web.profile.dto.AlbumDto
import photo.world.web.profile.dto.ServiceDto

data class GetOtherProfileDto(
    val name: String,
    val rating: Float,
    @JsonProperty("comments_number")
    val commentsNumber: Int,
    val albums: List<AlbumDto>,
    @JsonProperty("about_me")
    val aboutMe: String,
    val services: List<ServiceDto>,
    @JsonProperty("work_experience")
    val workExperience: Int,
    @JsonProperty("model_params")
    val modelParams: ModelProfile.Params? = null,
)