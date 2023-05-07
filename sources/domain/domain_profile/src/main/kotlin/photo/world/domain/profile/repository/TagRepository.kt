package photo.world.domain.profile.repository

import photo.world.domain.profile.entity.Tag
import photo.world.domain.profile.entity.profile.ProfileType

interface TagRepository {

    fun getTags(profileType: ProfileType): List<Tag>
}