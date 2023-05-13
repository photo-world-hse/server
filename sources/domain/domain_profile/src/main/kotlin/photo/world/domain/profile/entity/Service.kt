package photo.world.domain.profile.entity

data class Service<T>(
    val name: String,
    val cost: T?,
    val payType: PayType,
)

enum class PayType {
    BY_SERVICE,
    BY_HOUR,
}