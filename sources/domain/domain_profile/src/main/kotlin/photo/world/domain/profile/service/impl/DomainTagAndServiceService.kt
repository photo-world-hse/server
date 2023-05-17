package photo.world.domain.profile.service.impl

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.repository.ServiceRepository
import photo.world.domain.profile.repository.TagRepository
import photo.world.domain.profile.service.TagAndServiceService

class DomainTagAndServiceService(
    private val tagRepository: TagRepository,
    private val serviceRepository: ServiceRepository,
) : TagAndServiceService {

    override fun getAllTags(profileType: ProfileType): List<String> =
        tagRepository.getTags(profileType).map { it.name }

    override fun getAllServices(profileType: ProfileType): List<String> =
        serviceRepository.getServices(profileType)
}