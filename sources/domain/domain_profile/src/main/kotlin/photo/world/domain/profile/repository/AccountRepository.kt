package photo.world.domain.profile.repository

import photo.world.domain.profile.entity.Account
import photo.world.domain.profile.entity.profile.Profile
import photo.world.domain.profile.entity.profile.ProfileType

interface AccountRepository {

    fun getProfilesForAccount(accountEmail: String): List<Profile>

    fun getProfile(accountEmail: String, profileType: ProfileType): Profile

    fun getAccount(accountEmail: String): Account

    fun update(account: Account)
}