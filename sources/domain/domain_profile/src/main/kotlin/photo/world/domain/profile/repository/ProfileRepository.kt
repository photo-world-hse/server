package photo.world.domain.profile.repository

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.profile.LiteProfile
import photo.world.domain.profile.entity.profile.Profile

interface ProfileRepository {

    fun saveNew(accountEmail: String, profile: Profile)

    fun update(accountEmail: String, profile: Profile)

    fun delete(accountEmail: String, profile: Profile)

    fun getProfile(accountEmail: String, profileType: ProfileType): Profile

    fun findBySearchParams(
        name: String,
        profileType: ProfileType,
        workExperience: IntRange,
    ): List<LiteProfile>
}