package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangeModelParamsRequestDto(
    val height: Int,
    @JsonProperty("hip_girth")
    val hipGirth: Int,
    val bust: Int,
    @JsonProperty("waist_circumference")
    val waistCircumference: Int,
    @JsonProperty("hair_color")
    val hairColor: String,
    @JsonProperty("eye_color")
    val eyeColor: String,
)
