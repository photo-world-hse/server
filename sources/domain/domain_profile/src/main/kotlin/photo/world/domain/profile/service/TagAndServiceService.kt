package photo.world.domain.profile.service

import photo.world.common.profile.ProfileType

interface TagAndServiceService {

    fun getAllTags(profileType: ProfileType): List<String>

    fun getAllServices(profileType: ProfileType): List<String>
}