package photo.world.domain.profile.entity

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import photo.world.domain.errors.DomainException
import photo.world.domain.profile.entity.profile.PhotographerProfile
import photo.world.domain.profile.entity.profile.ProfileType
import photo.world.domain.profile.entity.profile.VisagistProfile
import photo.world.domain.profile.service.data.ProfileData

class AccountDomainSpec : BehaviorSpec({
    val account = Account(
        name = "Roman Nazmutdinov",
        email = "nazroman2002@gmail.com",
        telephone = null,
        profiles = listOf(),
    )

    Given("Empty account") {
        val profileData = ProfileData.VisagistData(
            avatarUrl = null,
            tags = listOf(),
            services = listOf(),
            photos = listOf(),
            aboutMe = "aboutMe",
            workExperience = 10,
            additionalInfo = "additionalInfo",
        )
        When("Add first visagist profile") {
            account.addProfile(profileData)
            Then("Account has profile") {
                val createdProfile = account.getProfile(ProfileType.VISAGIST)
                val testProfile = VisagistProfile.createNewProfile(profileData)
                createdProfile.aboutMe shouldBe testProfile.aboutMe
                createdProfile.workExperience shouldBe testProfile.workExperience
                createdProfile.additionalInfo shouldBe testProfile.additionalInfo
            }
        }
        val photographerProfileData = ProfileData.PhotographerData(
            avatarUrl = null,
            tags = listOf(),
            services = listOf(),
            photos = listOf(),
            aboutMe = "aboutMe",
            workExperience = 10,
            additionalInfo = "additionalInfo",
        )
        When("Add photographer profile") {
            account.addProfile(photographerProfileData)
            Then("Account has profile") {
                val createdProfile = account.getProfile(ProfileType.PHOTOGRAPHER)
                val testProfile = PhotographerProfile.createNewProfile(photographerProfileData)
                createdProfile.aboutMe shouldBe testProfile.aboutMe
                createdProfile.workExperience shouldBe testProfile.workExperience
                createdProfile.additionalInfo shouldBe testProfile.additionalInfo
            }
        }
        When("Add one more visagist profile") {
            Then("Throw domain exception") {
                shouldThrow<DomainException> {
                    account.addProfile(profileData)
                }
            }
        }
        When("Get not existing profile") {
            Then("Throw domain exception") {
                shouldThrow<DomainException> {
                    account.getProfile(ProfileType.MODEL)
                }
            }
        }
    }
})