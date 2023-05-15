package photo.world.web.profile

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import photo.world.domain.errors.withValidation
import photo.world.domain.profile.service.SearchService
import photo.world.domain.profile.service.data.SearchParams
import photo.world.web.profile.dto.LiteProfileDto
import photo.world.web.profile.dto.request.SearchRequestDto
import photo.world.web.profile.dto.response.SearchResponseDto
import photo.world.web.profile.utils.ProfileValidator
import photo.world.web.profile.utils.getProfileTypeByName
import photo.world.web.profile.utils.toServiceDto

@RestController
@RequestMapping("/api/v1/search")
internal class SearchController(
    private val searchService: SearchService,
) {

    @PostMapping
    fun search(@RequestBody request: SearchRequestDto): ResponseEntity<SearchResponseDto> =
        withValidation(request, ProfileValidator::validateSearchRequestDto) {
            val profiles = searchService.search(
                SearchParams(
                    name = request.searchQuery,
                    tags = request.tags,
                    services = request.services,
                    startWorkExperience = request.startWorkExperience ?: Int.MIN_VALUE,
                    endWorkExperience = request.endWorkExperience ?: Int.MAX_VALUE,
                    profileType = getProfileTypeByName(request.profileType),
                )
            )
            val liteProfilesDto = profiles.map { liteProfile ->
                LiteProfileDto(
                    name = liteProfile.name,
                    email = liteProfile.email,
                    avatarUrl = liteProfile.avatarUrl,
                    photos = liteProfile.photos,
                    services = liteProfile.services.map { it.toServiceDto() },
                    rating = liteProfile.rating,
                    commentsNumber = liteProfile.commentsNumber,
                )
            }
            return ResponseEntity.ok(SearchResponseDto(liteProfilesDto))
        }
}