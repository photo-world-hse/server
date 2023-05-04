package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.web.profile.dto.ServiceDto

internal data class CreateModelRequestDto(
    val tags: List<String>,
    @JsonProperty("about_me") val aboutMe: String,
    @JsonProperty("work_experience") val workExperience: Int,
    @JsonProperty("extra_info") val extraInfo: String,
    val photos: List<String>,
    val height: Int,
    @JsonProperty("hip_girth") val hipGirth: Int,
    val bust: Int,
    @JsonProperty("waist_circumference") val waistCircumference: Int,
    @JsonProperty("hair_color") val hairColor: String,
    @JsonProperty("eye_color") val eyeColor: String,
    @JsonProperty("avatar_url") val avatarUrl: String,
    val services: List<ServiceDto>,
)
