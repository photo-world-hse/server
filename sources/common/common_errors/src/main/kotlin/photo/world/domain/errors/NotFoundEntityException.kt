package photo.world.domain.errors

class NotFoundEntityException(
    message: String?,
    throwable: Throwable? = null,
) : RuntimeException(message, throwable)