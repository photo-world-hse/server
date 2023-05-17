package photo.world.data.user.entity.profile

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "album")
data class DataAlbum(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "album_id")
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    val photos: List<DataPhoto>,
)