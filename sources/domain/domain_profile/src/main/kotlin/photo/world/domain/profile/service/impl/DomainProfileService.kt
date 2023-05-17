package photo.world.domain.profile.service.impl

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.profile.Profile
import photo.world.domain.profile.repository.AccountRepository
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.service.ProfileService
import photo.world.domain.profile.service.data.ProfileData

class DomainProfileService(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository,
) : ProfileService {

    override fun createNewProfile(
        accountEmail: String,
        profileData: ProfileData,
    ): Profile {
        val account = accountRepository.getAccount(accountEmail)
        val profile = account.addProfile(profileData)
        profileRepository.saveNew(accountEmail, profile)
        return profile
    }

    override fun deleteProfile(accountEmail: String, profileType: ProfileType) {
        val account = accountRepository.getAccount(accountEmail)
        val profile = account.deleteProfile(profileType)
        accountRepository.update(account)
        profileRepository.delete(accountEmail, profile)
    }
}