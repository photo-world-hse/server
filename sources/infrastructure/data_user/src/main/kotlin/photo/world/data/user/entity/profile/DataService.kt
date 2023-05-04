package photo.world.data.user.entity.profile

import jakarta.persistence.*

@Entity
@Table(name = "service")
data class DataService(
    @Id
    val name: String,
    val isPhotographerService: Boolean = false,
    val isVisagistService: Boolean = false,
    val isModelService: Boolean = false,
)