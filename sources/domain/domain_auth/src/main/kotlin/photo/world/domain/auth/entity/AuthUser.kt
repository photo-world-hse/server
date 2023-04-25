package photo.world.domain.auth.entity

import photo.world.domain.auth.repository.AuthUserRepository
import photo.world.domain.auth.repository.TokenRepository
import photo.world.domain.auth.utils.ActivationCodeGenerator
import photo.world.domain.errors.DomainException

class AuthUser private constructor(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val role: Role,
    activationCode: String
) {

    var activationCode: String = activationCode
        private set
    var isActivatedUser: Boolean = false
        private set

    fun activateUser(activationCode: String) {
        when {
            isActivatedUser ->
                throw DomainException("User already activated")
            this.activationCode != activationCode ->
                throw DomainException("Activation code is not valid")
            this.activationCode == activationCode -> isActivatedUser = true
        }
    }

    fun logout(tokenRepository: TokenRepository) {
        val tokens = tokenRepository.findAllValidTokensByUser(email)
        tokens.forEach { token ->
            token.revokeToken()
            tokenRepository.update(token)
        }
    }

    fun createNewActivationCode() {
        activationCode = ActivationCodeGenerator.generateNewCode()
    }

    companion object {

        fun createNewUser(
            name: String,
            email: String,
            password: String,
            userRepository: AuthUserRepository? = null,
        ): AuthUser {
            if (userRepository?.findUserByEmail(email)?.isActivatedUser == true) {
                throw DomainException("Activated user with email $email already exists")
            } else {
                val activationCode = ActivationCodeGenerator.generateNewCode()
                return AuthUser(
                    id = activationCode,
                    name = name,
                    email = email,
                    password = password,
                    role = Role.USER,
                    activationCode = activationCode,
                )
            }
        }

        fun createExistingUser(
            id: String,
            name: String,
            email: String,
            password: String,
            activationCode: String,
            isActivated: Boolean,
        ) = AuthUser(
            id = id,
            name = name,
            email = email,
            password = password,
            role = Role.USER,
            activationCode = activationCode,
        ).apply {
            isActivatedUser = isActivated
        }
    }
}