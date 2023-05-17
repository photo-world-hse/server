package photo.world.data.user.entity.photosession

import jakarta.persistence.*
import photo.world.data.user.entity.profile.DataPhoto
import photo.world.data.user.entity.profile.DataProfile
import java.util.*

@Entity
@Table(name = "photosession")
data class DataPhotosession(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "photosession_id")
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val duration: Float,
    val address: String,
    val startDateAndTime: Date,
    val isFinished: Boolean,
    @ManyToOne
    @JoinColumn(name = "profile_id")
    val organizer: DataProfile,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "photosession_photos_id", referencedColumnName = "photosession_id")
    val photos: List<DataPhoto> = listOf(),
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "photosession_results_id", referencedColumnName = "photosession_id")
    val resultPhotos: List<DataPhoto> = listOf(),
) {

    @ManyToMany
    @JoinTable(
        name = "PhotosessionTags",
        joinColumns = [JoinColumn(name = "photosession_id", referencedColumnName = "photosession_id")],
        inverseJoinColumns = [JoinColumn(name = "photosession_tag_name", referencedColumnName = "photosession_tag_name")],
    )
    var tags: List<DataPhotosessionTag> = listOf()

    @OneToMany(cascade = [CascadeType.ALL])
    var participateRelationships: List<ParticipateRelationship> = listOf()
}