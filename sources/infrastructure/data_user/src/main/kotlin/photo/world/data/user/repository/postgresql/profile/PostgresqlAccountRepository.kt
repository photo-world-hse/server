package photo.world.data.user.repository.postgresql.profile

import org.springframework.stereotype.Component
import photo.world.common.profile.ProfileType
import photo.world.data.user.entity.profile.DataProfile
import photo.world.data.user.entity.user.BaseUser
import photo.world.data.user.repository.spring.SpringDataPostgresqlUserRepository
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlProfileRepository
import photo.world.data.user.utils.toAccount
import photo.world.data.user.utils.toProfile
import photo.world.data.user.utils.updateUserData
import photo.world.domain.errors.notFound
import photo.world.domain.profile.entity.Account
import photo.world.domain.profile.entity.profile.Profile
import photo.world.domain.profile.repository.AccountRepository

@Component
internal class PostgresqlAccountRepository constructor(
    private val springDataPostgresqlUserRepository: SpringDataPostgresqlUserRepository,
    private val springDataPostgresqlProfileRepository: SpringDataPostgresqlProfileRepository,
) : AccountRepository {

    override fun getProfilesForAccount(accountEmail: String): List<Profile> {
        val dataProfiles = springDataPostgresqlProfileRepository.findAllProfilesByUser(accountEmail)
        return dataProfiles.map { it.toDomainProfile() }
    }

    override fun getProfile(accountEmail: String, profileType: ProfileType): Profile {
        val profile =
            springDataPostgresqlProfileRepository.findProfile(accountEmail, profileType)
                ?: notFound<DataProfile>("user email: $accountEmail, profile type: ${profileType.name}")
        return profile.toDomainProfile()
    }

    override fun getAccount(accountEmail: String): Account {
        val user = springDataPostgresqlUserRepository.findByEmail(accountEmail)
            ?: notFound<BaseUser>("email: $accountEmail")
        val profiles = springDataPostgresqlProfileRepository.findAllProfilesByUser(accountEmail)
        return user.toAccount(profiles = profiles.map { it.toDomainProfile() })
    }

    override fun update(account: Account) {
        val baseUser = springDataPostgresqlUserRepository.findByEmail(account.email)
            ?: notFound<BaseUser>("email: ${account.email}")
        val updatedBaseUser = baseUser.updateUserData(account)
        springDataPostgresqlUserRepository.save(updatedBaseUser)
    }

    private fun DataProfile.toDomainProfile(): Profile {
        val profileServiceRelationships =
            springDataPostgresqlProfileRepository.findProfileServiceRelationships(id)
        return toProfile(profileServiceRelationships)
    }
}