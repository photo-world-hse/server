package photo.world.web.profile.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.domain.profile.entity.profile.ModelProfile
import photo.world.web.profile.dto.ServiceDto

data class GetProfileInfoResponseDto(
    @JsonProperty("about_me") val aboutMe: String,
    @JsonProperty("extra_info") val extraInfo: String,
    val tags: List<String>,
    val services: List<ServiceDto>,
    val experience: Int,
    @JsonProperty("model_params") val modelParams: ModelProfile.Params? = null,
)