package photo.world.web.profile.dto

import com.fasterxml.jackson.annotation.JsonProperty
import photo.world.domain.profile.entity.PayType
import photo.world.domain.profile.entity.Service

data class ServiceDto(
    val name: String,
    @JsonProperty("start_price")
    val startPrice: Int,
    @JsonProperty("end_price")
    val endPrice: Int?, // if not null then it is range
    @JsonProperty("pay_type")
    val payType: String,
) {

    internal fun toDomainModel(): Service<*> {
        val payType = PayType.valueOf(this.payType)
        val service = if (endPrice == null) {
            Service(
                name = name,
                cost = startPrice,
                payType = payType,
            )
        } else {
            Service(
                name = name,
                cost = startPrice..endPrice,
                payType = payType,
            )
        }
        return service
    }
}
