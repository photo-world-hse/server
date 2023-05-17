package photo.world.web.profile

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import photo.world.domain.errors.withValidation
import photo.world.domain.profile.entity.profile.*
import photo.world.domain.profile.service.ProfileContentService
import photo.world.domain.profile.service.ProfileService
import photo.world.web.profile.dto.ServiceDto
import photo.world.web.profile.dto.request.*
import photo.world.web.profile.dto.response.GetOtherProfileDto
import photo.world.web.profile.dto.response.GetPhotosResponseDto
import photo.world.web.profile.dto.response.GetProfileInfoResponseDto
import photo.world.web.profile.dto.response.GetProfilesResponseDto
import photo.world.web.profile.utils.*
import photo.world.web.profile.utils.ProfileValidator
import photo.world.web.profile.utils.RequestToProfileDataMapper
import photo.world.web.profile.utils.getProfileTypeByName
import photo.world.web.profile.utils.toResponseMap

@RestController
@RequestMapping("/api/v1/profiles")
internal class ProfileController(
    val profileService: ProfileService,
    val profileContentService: ProfileContentService,
) {

    @GetMapping
    fun getAccountProfiles(authentication: Authentication): ResponseEntity<GetProfilesResponseDto> {
        val profiles = profileContentService.getProfiles(authentication.name)
        val name = profileContentService.getProfileName(authentication.name)
        return ResponseEntity.ok(
            GetProfilesResponseDto(
                name = name,
                profiles = profiles.toResponseMap(),
            )
        )
    }

    @PutMapping("/model")
    fun createModel(
        @RequestBody request: CreateModelRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> =
        withValidation(request, ProfileValidator::validateCreateModelRequestDto) { validatedRequest ->
            profileService.createNewProfile(
                accountEmail = authentication.name,
                profileData = RequestToProfileDataMapper.requestToProfileData(validatedRequest),
            )
            return ResponseEntity.ok().build()
        }

    @PutMapping("/photographer")
    fun createPhotographer(
        @RequestBody request: CreatePhotographerRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> =
        withValidation(request, ProfileValidator::validateCreatePhotographerRequestDto) { validatedRequest ->
            profileService.createNewProfile(
                accountEmail = authentication.name,
                profileData = RequestToProfileDataMapper.requestToProfileData(validatedRequest),
            )
            return ResponseEntity.ok().build()
        }

    @PutMapping("/visagist")
    fun createVisagist(
        @RequestBody request: CreateVisagistRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> =
        withValidation(request, ProfileValidator::validateCreateVisagistRequestDto) { validatedRequest ->
            profileService.createNewProfile(
                accountEmail = authentication.name,
                profileData = RequestToProfileDataMapper.requestToProfileData(validatedRequest),
            )
            return ResponseEntity.ok().build()
        }

    @GetMapping("/{profileName}/info")
    fun getProfileInfo(
        @PathVariable profileName: String,
        authentication: Authentication,
    ): ResponseEntity<GetProfileInfoResponseDto> {
        val getProfileDto = getProfileDto(authentication.name, profileName) { profile ->
            GetProfileInfoResponseDto(
                modelParams = (profile as? ModelProfile)?.modelParams,
                aboutMe = profile.aboutMe,
                experience = profile.workExperience,
                extraInfo = profile.additionalInfo,
                tags = profile.tags.map { it.name },
                services = profile.services.map { it.toServiceDto() },
            )
        }
        return ResponseEntity.ok(getProfileDto)
    }

    @GetMapping("/{email}/{profileName}/info")
    fun getProfileInfo(
        @PathVariable profileName: String,
        @PathVariable email: String,
    ): ResponseEntity<GetOtherProfileDto> {
        val name = profileContentService.getProfileName(email)
        val getProfileDto = getProfileDto(email, profileName) { profile ->
            GetOtherProfileDto(
                name = name,
                rating = profile.rating,
                commentsNumber = profile.commentNumber,
                albums = profile.albums.map { album -> album.toDto() },
                aboutMe = profile.aboutMe,
                services = profile.services.map { it.toServiceDto() },
                workExperience = profile.workExperience,
                modelParams = (profile as? ModelProfile)?.modelParams,
                comments = profile.comments.map { it.toDto() }
            )
        }
        return ResponseEntity.ok(getProfileDto)
    }

    @GetMapping("/{profileName}/photos")
    fun getPhotos(
        @PathVariable profileName: String,
        authentication: Authentication,
    ): ResponseEntity<GetPhotosResponseDto> {
        val profileType = getProfileTypeByName(profileName)
        val photos = profileContentService.getAllPhotos(
            accountEmail = authentication.name,
            profileType = profileType,
        )
        return ResponseEntity.ok(GetPhotosResponseDto(allPhotos = photos))
    }

    @DeleteMapping("/{profileName}/photos")
    fun deletePhoto(
        @RequestBody request: DeletePhotoRequestDto,
        @PathVariable profileName: String,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        profileContentService.deletePhoto(
            accountEmail = authentication.name,
            profileType = profileType,
            photoUrl = request.photoUrl,
        )
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{profileName}/photos")
    fun addPhoto(
        @RequestBody request: AddPhotoRequestDto,
        @PathVariable profileName: String,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        profileContentService.addPhotos(
            accountEmail = authentication.name,
            profileType = profileType,
            newPhotos = request.photos,
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{profileName}/tags")
    private fun changeTags(
        @PathVariable profileName: String,
        @RequestBody request: ChangeTagsRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        profileContentService.changeTags(
            accountEmail = authentication.name,
            profileType = profileType,
            tags = request.newTags,
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{profileName}/services")
    private fun changeServices(
        @PathVariable profileName: String,
        @RequestBody request: ChangeServicesRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        profileContentService.changeServices(
            accountEmail = authentication.name,
            profileType = profileType,
            services = request.newServices.map(ServiceDto::toDomainModel)
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{profileName}/info")
    private fun changeProfileInfo(
        @PathVariable profileName: String,
        @RequestBody request: ChangeProfileInfoRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        profileContentService.changeProfileInfo(
            accountEmail = authentication.name,
            profileType = profileType,
            aboutMe = request.aboutMe,
            workExperience = request.workExperience,
            additionalInfo = request.extraInfo,
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{profileName}/avatar")
    private fun changeAvatar(
        @PathVariable profileName: String,
        @RequestBody request: ChangeAvatarUrlRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        profileContentService.changeProfileAvatar(
            accountEmail = authentication.name,
            profileType = profileType,
            newAvatarUrl = request.avatarUrl,
        )
        return ResponseEntity.ok().build()
    }

    @PostMapping("/modelParams")
    private fun changeModelParams(
        @RequestBody request: ChangeModelParamsRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        profileContentService.changeModelParams(
            accountEmail = authentication.name,
            modelParams = ModelProfile.Params(
                height = request.height,
                hairColor = request.hairColor,
                eyeColor = request.eyeColor,
                bust = request.bust,
                hipGirth = request.hipGirth,
                waistCircumference = request.waistCircumference,
            )
        )
        return ResponseEntity.ok().build()
    }

    private fun <T> getProfileDto(
        email: String,
        profileName: String,
        profileToDtoMapper: (Profile) -> T,
    ): T {
        val profileType = getProfileTypeByName(profileName)
        val profile = profileContentService.getProfile(
            accountEmail = email,
            profileType = profileType,
        )
        return profileToDtoMapper(profile)
    }
}