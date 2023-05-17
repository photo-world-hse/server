package photo.world.data.user.entity.photosession

import jakarta.persistence.*

@Entity
@Table(name = "photosession_tag")
data class DataPhotosessionTag(
    @Id
    @Column(name = "photosession_tag_name")
    val name: String,
    @ManyToMany(mappedBy = "tags")
    val photosessions: List<DataPhotosession>,
)