package photo.world.data.user.entity.profile

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "photo")
data class DataPhoto(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "photo_id")
    val id: String = UUID.randomUUID().toString(),
    val url: String,
)