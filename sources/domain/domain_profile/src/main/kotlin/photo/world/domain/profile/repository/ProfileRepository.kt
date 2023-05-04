package photo.world.domain.profile.repository

import photo.world.domain.profile.entity.profile.Profile

interface ProfileRepository {

    fun saveNew(accountEmail: String, profile: Profile)

    fun update(accountEmail: String, profile: Profile)

    fun delete(accountEmail: String, profile: Profile)
}