package photo.world.data.user.entity.profile

import jakarta.persistence.*
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.user.BaseUser
import java.util.*

@Entity
@Table(name = "profile")
data class DataProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "profile_id")
    val id: String = UUID.randomUUID().toString(),
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: BaseUser,
    @Enumerated(EnumType.STRING)
    val profileType: ProfileType,
    val avatarUrl: String?,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    val photos: List<DataPhoto>,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    val albums: List<DataAlbum>,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    val comments: List<DataComment>,
    val aboutMe: String,
    val workExperience: Int,
    val additionalInfo: String,
    var height: Int? = null,
    var hairColor: String? = null,
    var eyeColor: String? = null,
    var bust: Int? = null,
    var hipGirth: Int? = null,
    var waistCircumference: Int? = null,
) {

    @ManyToMany
    @JoinTable(
        name = "ProfileTags",
        joinColumns = [JoinColumn(name = "profile_id", referencedColumnName = "profile_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_name", referencedColumnName = "tag_name")],
    )
    var tags: List<DataTag> = listOf()
}