package photo.world.domain.errors

class ForbiddenException(
    message: String,
    throwable: Throwable? = null,
) : RuntimeException(message, throwable)