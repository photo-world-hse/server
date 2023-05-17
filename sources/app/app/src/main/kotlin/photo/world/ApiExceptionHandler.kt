package photo.world

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import photo.world.domain.errors.DomainException
import photo.world.domain.errors.ForbiddenException
import photo.world.domain.errors.NotFoundEntityException
import photo.world.domain.errors.ValidationException
import java.lang.Exception
import java.time.ZoneId
import java.time.ZonedDateTime

data class ApiExceptionData(
    val message: String?,
    val errorName: String?,
    val httpStatus: HttpStatus,
    val zonedDateTime: ZonedDateTime,
)

@ControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(exception: ValidationException): ResponseEntity<ApiExceptionData> =
        generalExceptionHandler(
            httpStatus = HttpStatus.BAD_REQUEST,
            errorName = "ValidationError",
            exception = exception,
        )

    @ExceptionHandler(DomainException::class)
    fun handleDomainException(exception: DomainException): ResponseEntity<ApiExceptionData> =
        generalExceptionHandler(
            httpStatus = HttpStatus.BAD_REQUEST,
            errorName = "DomainError",
            exception = exception,
        )

    @ExceptionHandler(NotFoundEntityException::class)
    fun handleNotFoundEntityException(exception: NotFoundEntityException): ResponseEntity<ApiExceptionData> =
        generalExceptionHandler(
            httpStatus = HttpStatus.NOT_FOUND,
            errorName = "NotFoundEntityError",
            exception = exception,
        )

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(exception: IllegalStateException): ResponseEntity<ApiExceptionData> =
        generalExceptionHandler(
            httpStatus = HttpStatus.BAD_REQUEST,
            errorName = "IllegalStateError",
            exception = exception,
        )

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(exception: ForbiddenException): ResponseEntity<ApiExceptionData> =
        generalExceptionHandler(
            httpStatus = HttpStatus.FORBIDDEN,
            errorName = exception::class.simpleName.orEmpty(),
            exception = exception,
        )

    @ExceptionHandler(Exception::class)
    fun handleOtherException(exception: Exception): ResponseEntity<ApiExceptionData> =
        generalExceptionHandler(
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            errorName = exception::class.simpleName.orEmpty(),
            exception = exception,
        )

    private fun generalExceptionHandler(
        httpStatus: HttpStatus,
        errorName: String,
        exception: Exception,
    ): ResponseEntity<ApiExceptionData> {
        val exceptionData =
            ApiExceptionData(
                message = exception.message,
                errorName = errorName,
                httpStatus = httpStatus,
                zonedDateTime = ZonedDateTime.now(ZoneId.of("Z"))
            )
        println(exception.stackTrace)
        return ResponseEntity(exceptionData, httpStatus)
    }
}