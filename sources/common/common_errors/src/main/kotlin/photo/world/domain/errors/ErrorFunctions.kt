package photo.world.domain.errors

inline fun <T, R> withValidation(
    dto: R,
    validator: (R) -> Boolean,
    successAction: (R) -> T,
): T {
    if (validator(dto)) {
        return successAction(dto)
    } else {
        throw ValidationException("request data is not valid")
    }
}

inline fun <reified T> notFound(searchParams: String): Nothing =
    throw NotFoundEntityException("${T::class.simpleName} with $searchParams not found")