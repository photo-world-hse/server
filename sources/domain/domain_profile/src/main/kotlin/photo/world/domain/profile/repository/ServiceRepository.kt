package photo.world.domain.profile.repository

import photo.world.domain.profile.entity.profile.ProfileType

interface ServiceRepository {

    fun getServices(profileType: ProfileType): List<String>
}