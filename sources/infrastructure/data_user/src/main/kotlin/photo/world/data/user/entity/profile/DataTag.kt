package photo.world.data.user.entity.profile

import jakarta.persistence.*

@Entity
@Table(name = "tag")
data class DataTag(
    @Id
    @Column(name = "tag_name")
    val name: String,
    @ManyToMany(mappedBy = "tags")
    val profiles: List<DataProfile>,
    val isPhotographerTag: Boolean = false,
    val isVisagistTag: Boolean = false,
    val isModelTag: Boolean = false,
)