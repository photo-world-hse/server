package photo.world.domain.profile.repository

import photo.world.common.profile.ProfileType

interface ServiceRepository {

    fun getServices(profileType: ProfileType): List<String>
}