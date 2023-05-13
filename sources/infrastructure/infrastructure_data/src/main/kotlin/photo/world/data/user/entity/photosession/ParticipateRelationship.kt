package photo.world.data.user.entity.photosession

import jakarta.persistence.*
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.entity.profile.DataService
import photo.world.domain.photosession.entity.InviteStatus
import photo.world.domain.profile.entity.PayType

@Entity
@Table(name = "participate_relationship")
data class ParticipateRelationship(
    @Enumerated(EnumType.STRING)
    val inviteStatus: InviteStatus,
    @ManyToOne
    val profile: DataProfile,
    @ManyToOne(targetEntity = DataPhotosession::class, fetch = FetchType.EAGER)
    val photosession: DataPhotosession,

    @Id
    @Column(name = "photosession_participate_id")
    val id: String = "${photosession.name}${profile.id}",
)