package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ChangeTagsRequestDto(
    @JsonProperty("new_tags") val newTags: List<String>,
)
