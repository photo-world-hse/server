package photo.world.web.user.service.dto

import com.fasterxml.jackson.annotation.JsonProperty

internal data class VerifyRequestDto(
    val email: String,
    @JsonProperty("activation_code") val activationCode: String,
)