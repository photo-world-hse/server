package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

internal data class SearchRequestDto(
    @JsonProperty("search_query")
    val searchQuery: String,
    val tags: List<String>,
    val services: List<String>,
    @JsonProperty("start_work_experience")
    val startWorkExperience: Int?,
    @JsonProperty("end_work_experience")
    val endWorkExperience: Int?,
    @JsonProperty("profile_type")
    val profileType: String,
)
