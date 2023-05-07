package photo.world.data.user.entity.profile

import jakarta.persistence.*
import photo.world.domain.profile.entity.PayType
import java.util.*

@Entity
@Table(name = "profile_service")
data class ProfileServiceRelationship(
    @Enumerated(EnumType.STRING)
    val payType: PayType,
    val startCost: Int,
    val endCost: Int?,
    @ManyToOne(fetch = FetchType.EAGER)
    val profile: DataProfile,
    @ManyToOne(targetEntity = DataService::class, fetch = FetchType.EAGER)
    val service: DataService,

    @Id
    @Column(name = "profile_service_id")
    val id: String = "${service.name}${profile.id}",
)