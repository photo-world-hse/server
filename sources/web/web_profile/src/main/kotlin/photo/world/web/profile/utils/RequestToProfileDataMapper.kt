package photo.world.web.profile.utils

import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.entity.profile.ModelProfile
import photo.world.domain.profile.service.data.ProfileData
import photo.world.web.profile.dto.ServiceDto
import photo.world.web.profile.dto.request.CreateModelRequestDto
import photo.world.web.profile.dto.request.CreatePhotographerRequestDto
import photo.world.web.profile.dto.request.CreateVisagistRequestDto

internal object RequestToProfileDataMapper {

    fun requestToProfileData(request: CreateModelRequestDto): ProfileData.ModelData =
            ProfileData.ModelData(
                avatarUrl = request.avatarUrl,
                tags = request.tags.map { Tag(it) },
                services = request.services.map(ServiceDto::toDomainModel),
                workExperience = request.workExperience,
                additionalInfo = request.extraInfo,
                photos = request.photos,
                aboutMe = request.aboutMe,
                modelParams = ModelProfile.Params(
                    height = request.height,
                    hairColor = request.hairColor,
                    eyeColor = request.eyeColor,
                    bust = request.bust,
                    hipGirth = request.hipGirth,
                    waistCircumference = request.waistCircumference,
                ),
            )

    fun requestToProfileData(request: CreatePhotographerRequestDto): ProfileData.PhotographerData =
        ProfileData.PhotographerData(
            avatarUrl = request.avatarUrl,
            tags = request.tags.map { Tag(it) },
            services = request.services.map(ServiceDto::toDomainModel),
            workExperience = request.workExperience,
            additionalInfo = request.extraInfo,
            photos = request.photos,
            aboutMe = request.aboutMe,
        )

    fun requestToProfileData(request: CreateVisagistRequestDto): ProfileData.VisagistData =
        ProfileData.VisagistData(
            avatarUrl = request.avatarUrl,
            tags = request.tags.map { Tag(it) },
            services = request.services.map(ServiceDto::toDomainModel),
            workExperience = request.workExperience,
            additionalInfo = request.extraInfo,
            photos = request.photos,
            aboutMe = request.aboutMe,
        )
}