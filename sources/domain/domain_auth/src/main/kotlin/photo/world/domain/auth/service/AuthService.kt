package photo.world.domain.auth.service

import photo.world.domain.auth.entity.Token

interface AuthService {

    fun register(
        email: String,
        name: String,
        password: String,
    )

    fun authenticate(
        email: String,
        password: String,
    ): Token

    fun resendVerifyCode(email: String)

    fun verify(
        email: String,
        activationCode: String,
    ): Token

    fun logout(email: String)
}