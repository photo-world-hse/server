package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangeProfileInfoRequestDto(
    @JsonProperty("about_me")
    val aboutMe: String,
    @JsonProperty("work_experience")
    val workExperience: Int,
    @JsonProperty("extra_info")
    val extraInfo: String,
)