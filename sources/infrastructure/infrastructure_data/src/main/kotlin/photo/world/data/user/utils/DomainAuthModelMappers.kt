package photo.world.data.user.utils

import photo.world.data.user.entity.token.Token
import photo.world.data.user.entity.user.BaseUser
import photo.world.domain.auth.entity.AuthUser
import photo.world.domain.auth.entity.Token as DomainToken

internal fun BaseUser.toDomain(): AuthUser =
    AuthUser.createExistingUser(
        id = id,
        name = name,
        email = email,
        password = password,
        activationCode = activationCode,
        isActivated = isActivatedUser,
        chatAccessToken = chatAccessToken,
    )

internal fun AuthUser.newProfileToData(): BaseUser =
    BaseUser(
        id = id,
        email = email,
        name = name,
        activationCode = activationCode,
        password = password,
        isActivatedUser = isActivatedUser,
        chatAccessToken = chatAccessToken,
    )

internal fun Token.toDomain(): DomainToken =
    DomainToken.createExistingToken(
        token = token,
        expired = expired,
        revoked = revoked,
    )