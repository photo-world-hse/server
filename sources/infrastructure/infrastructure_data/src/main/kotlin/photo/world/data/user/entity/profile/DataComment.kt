package photo.world.data.user.entity.profile

import jakarta.persistence.*
import photo.world.common.profile.ProfileType
import java.util.*

@Entity
@Table(name = "comment")
data class DataComment(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id")
    val id: String = UUID.randomUUID().toString(),
    val writerEmail: String,
    val writerName: String,
    val writerAvatar: String?,
    val isAnonymous: Boolean,
    val date: Date,
    val grade: Int,
    val text: String,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    val photos: List<DataPhoto>,
)