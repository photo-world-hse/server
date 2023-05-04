package photo.world.web.profile

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import photo.world.domain.profile.service.TagAndServiceService
import photo.world.web.profile.dto.response.GetServicesResponseDto
import photo.world.web.profile.dto.response.GetTagsResponseDto
import photo.world.web.profile.utils.getProfileTypeByName

@RestController
@RequestMapping("/api/v1/profiles/{profileName}")
class TagsAndServicesController(
    private val tagAndServiceService: TagAndServiceService,
) {

    @GetMapping("/tags")
    fun getAllTags(@PathVariable profileName: String): ResponseEntity<GetTagsResponseDto> {
        val profileType = getProfileTypeByName(profileName)
        val tags = tagAndServiceService.getAllTags(profileType)
        return ResponseEntity.ok(GetTagsResponseDto(tags))
    }

    @GetMapping("/services")
    fun getAllServices(@PathVariable profileName: String): ResponseEntity<GetServicesResponseDto> {
        val profileType = getProfileTypeByName(profileName)
        val services = tagAndServiceService.getAllServices(profileType)
        return ResponseEntity.ok(GetServicesResponseDto(services))
    }
}