package photo.world.web.profile.utils

import photo.world.web.profile.dto.request.*
import photo.world.web.profile.dto.request.CreateModelRequestDto
import photo.world.web.profile.dto.request.CreatePhotographerRequestDto
import photo.world.web.profile.dto.request.CreateVisagistRequestDto

internal object ProfileValidator {

    fun validateCreateModelRequestDto(
        request: CreateModelRequestDto,
    ): Boolean = true //TODO: add validations

    fun validateCreateVisagistRequestDto(
        request: CreateVisagistRequestDto,
    ): Boolean = true //TODO: add validations

    fun validateCreatePhotographerRequestDto(
        request: CreatePhotographerRequestDto,
    ): Boolean = true //TODO: add validations

    fun validateCreateAlbumRequestDto(
        request: CreateAlbumRequestDto,
    ): Boolean = true //TODO: add validations

    fun validateChangeTagsRequestDto(
        request: ChangeTagsRequestDto,
    ): Boolean = true //TODO: add validations
}