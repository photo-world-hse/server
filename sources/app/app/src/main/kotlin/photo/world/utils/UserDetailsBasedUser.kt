package photo.world.utils

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.entity.Role

internal data class UserDetailsBasedUser(
    private val name: String,
    private val email: String,
    private val password: String,
    private val role: Role,
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

internal fun AuthUser.toUserDetails(): UserDetailsBasedUser =
    UserDetailsBasedUser(
        name = name,
        email = email,
        password = password,
        role = role,
    )