package photo.world.domain.profile.service.impl

import photo.world.domain.profile.entity.profile.LiteProfile
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.service.SearchService
import photo.world.domain.profile.service.data.SearchParams

class DomainSearchService(
    private val profileRepository: ProfileRepository,
) : SearchService {

    override fun search(searchParams: SearchParams): List<LiteProfile> =
        profileRepository.findBySearchParams(
            name = searchParams.name,
            profileType = searchParams.profileType,
            workExperience = with(searchParams) { startWorkExperience..endWorkExperience },
        )
            .asSequence()
            .filter { profile ->
                searchParams.tags.isEmpty() || profile.tags.containsAll(searchParams.tags)
            }
            .filter { profile ->
                val servicesName = profile.services.map { it.name }
                searchParams.services.isEmpty() || servicesName.containsAll(searchParams.services)
            }
            .sortedBy { it.rating }
            .toList()
}