package photo.world.domain.profile.repository

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.Tag

interface TagRepository {

    fun getTags(profileType: ProfileType): List<Tag>
}