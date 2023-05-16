package photo.world.data.user.entity.user

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import photo.world.data.user.entity.token.Token
import photo.world.domain.auth.entity.Role
import java.util.*

@Entity
@Table(name = "_user")
data class BaseUser(
    @Id
    @Column(name = "user_id")
    val id: String = UUID.randomUUID().toString(),
    @Column(unique = true)
    val email: String,
    @JvmField val password: String,
    val name: String,
    @Enumerated(EnumType.STRING)
    val role: Role = Role.USER,
    val activationCode: String,
    val isActivatedUser: Boolean,
    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
    )
    val tokens: List<Token> = listOf(),
    val chatAccessToken: String?,
    val telephone: String? = null,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String = password

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}
