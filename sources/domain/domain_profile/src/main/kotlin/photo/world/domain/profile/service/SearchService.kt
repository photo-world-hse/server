package photo.world.domain.profile.service

import photo.world.domain.profile.entity.profile.LiteProfile
import photo.world.domain.profile.service.data.SearchParams

interface SearchService {

    fun search(searchParams: SearchParams): List<LiteProfile>
}
