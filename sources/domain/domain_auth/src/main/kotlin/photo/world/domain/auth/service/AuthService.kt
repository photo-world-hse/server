package photo.world.domain.auth.service

import photo.world.domain.auth.service.data.AuthData

interface AuthService {

    fun register(
        email: String,
        name: String,
        password: String,
    )

    fun authenticate(
        email: String,
        password: String,
    ): AuthData

    fun resendVerifyCode(email: String)

    fun verify(
        email: String,
        activationCode: String,
    ): AuthData

    fun logout(email: String)
}