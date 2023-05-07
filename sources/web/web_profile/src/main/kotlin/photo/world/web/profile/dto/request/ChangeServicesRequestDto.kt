package photo.world.web.profile.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.web.profile.dto.ServiceDto

data class ChangeServicesRequestDto(
    @JsonProperty("new_services")
    val newServices: List<ServiceDto>,
)
