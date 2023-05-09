package photo.world.data.user.entity.token

import jakarta.persistence.*
import photo.world.data.user.entity.user.BaseUser
import photo.world.domain.auth.entity.TokenType
import java.util.*

@Entity
@Table(name = "token")
data class Token(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "token_id")
    val id: String = UUID.randomUUID().toString(),
    @Column(unique = true)
    val token: String,
    @Enumerated(EnumType.STRING)
    val tokenType: TokenType,
    val expired: Boolean,
    val revoked: Boolean,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: BaseUser
)