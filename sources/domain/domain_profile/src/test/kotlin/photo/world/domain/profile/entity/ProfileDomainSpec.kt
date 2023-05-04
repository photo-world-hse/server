package photo.world.domain.profile.entity

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import photo.world.domain.profile.entity.profile.PhotographerProfile
import photo.world.domain.profile.service.data.ProfileData

class ProfileDomainSpec : BehaviorSpec({
    val photographerProfile = PhotographerProfile.createNewProfile(
        profileData = ProfileData.PhotographerData(
            avatarUrl = null,
            tags = listOf(),
            services = listOf(),
            photos = listOf(),
            aboutMe = "aboutMe",
            workExperience = 10,
            additionalInfo = "additionalInfo",
        )
    )

    Given("New photographer profile without data") {
        // Profile info
        When("Set avatar url") {
            val newAvatarUrl = "avatarUrl2"
            photographerProfile.changeAvatar(newAvatarUrl)
            Then("Avatar was set") {
                photographerProfile.avatarUrl shouldBe newAvatarUrl
            }
        }
        When("Change avatar url") {
            val newAvatarUrl = "avatarUrl3"
            photographerProfile.changeAvatar(newAvatarUrl)
            Then("Avatar changed") {
                photographerProfile.avatarUrl shouldBe newAvatarUrl
            }
        }
        When("Change profile info") {
            val newAboutMe = "about me: lol kek cheburek"
            val newWorkExperience = 1
            val newAdditionalInfo = "new about me: lol kek cheburek"
            photographerProfile.changeProfileInfo(
                aboutMe = newAboutMe,
                workExperience = newWorkExperience,
                additionalInfo = newAdditionalInfo,
            )
            Then("Profile info changed") {
                photographerProfile.aboutMe shouldBe newAboutMe
                photographerProfile.workExperience shouldBe newWorkExperience
                photographerProfile.additionalInfo shouldBe newAdditionalInfo
            }
        }

        // Tags
        val newTags = listOf(
            "Tag1",
            "Tag2",
        )
        When("Add tags") {
            photographerProfile.changeTags(newTags)
            Then("Tags added") {
                val tags = newTags.map { Tag(it) }
                photographerProfile.tags.size shouldBe newTags.size
                photographerProfile.tags shouldContainAll tags
            }
        }
        When("Delete tags") {
            photographerProfile.changeTags(listOf())
            Then("Tags deleted") {
                photographerProfile.tags.size shouldBe 0
            }
        }

        //Services
        val newServices = listOf(
            Service(
                name = "Service 1",
                cost = 10,
                payType = PayType.BY_SERVICE,
            ),
            Service(
                name = "Service 2",
                cost = 10..20,
                payType = PayType.BY_SERVICE,
            ),
            Service(
                name = "Service 3",
                cost = 10..20,
                payType = PayType.BY_HOUR,
            ),
        )
        When("Add services") {
            photographerProfile.changeServices(newServices)
            Then("Services added") {
                photographerProfile.services.size shouldBe newServices.size
                photographerProfile.services shouldContainAll newServices
            }
        }
        When("Delete services") {
            photographerProfile.changeServices(listOf())
            Then("Services deleted") {
                photographerProfile.services.size shouldBe 0
            }
        }

        // Photos
        val newPhotos = listOf(
            "photo_url_1",
            "photo_url_2",
        )
        When("Change photos") {
            photographerProfile.addPhotos(newPhotos)
            Then("Photos added") {
                photographerProfile.photos.size shouldBe newPhotos.size
                photographerProfile.photos shouldContainAll newPhotos
            }
        }
        When("Delete photo") {
            photographerProfile.deletePhoto("photo_url_1")
            Then("Photo deleted") {
                photographerProfile.photos.size shouldBe (newPhotos.size - 1)
                photographerProfile.photos shouldNotContain "photo_url_1"
            }
            photographerProfile.deletePhoto("photo_url_2")
            Then("All photos deleted") {
                photographerProfile.photos.size shouldBe 0
            }
        }

        // Albums
        val albumName = "name1"
        val photos = listOf(
            "album photo 1",
            "album photo 2",
            "album photo 3",
        )
        When("Create album") {
            photographerProfile.createAlbum(albumName, photos)
            Then("Profile has album") {
                photographerProfile.albums.map { it.name } shouldContain albumName
                photographerProfile.getAlbum(albumName).photos shouldContainAll photos
            }
            Then("All photos contains photos from album") {
                photographerProfile.getAllPhotos() shouldContainAll photos
            }
        }
        val newAlbumName = "newAlbum name"
        When("Change album name") {
            photographerProfile.changeAlbumName(albumName, newAlbumName)
            Then("Album has new name") {
                photographerProfile.albums.map { it.name } shouldNotContain albumName
                photographerProfile.albums.map { it.name } shouldContain newAlbumName
                photographerProfile.getAlbum(newAlbumName).photos shouldContainAll photos
            }
        }
        When("Delete photo from profile") {
            photographerProfile.deletePhoto(photos.last())
            Then("Album don't has deleted photo") {
                photographerProfile.getAlbum(newAlbumName).photos shouldNotContain photos.last()
            }
            Then("Profile don't has deleted photo") {
                photographerProfile.getAllPhotos() shouldNotContain photos.last()
            }
            Then("Album has other photos") {
                photographerProfile.getAlbum(newAlbumName).photos shouldContainAll photos.dropLast(1)
            }
            Then("Profile has other photos") {
                photographerProfile.getAllPhotos() shouldContainAll photos.dropLast(1)
            }
        }
        When("Delete photo from album") {
            val deletedPhoto = photos[1]
            photographerProfile.deletePhotosFromAlbum(newAlbumName, photos.subList(1, 2))
            Then("Album don't has deleted photo") {
                photographerProfile.getAlbum(newAlbumName).photos shouldNotContain deletedPhoto
            }
            Then("Profile has photo") {
                photographerProfile.getAllPhotos() shouldContain deletedPhoto
            }
            Then("Album has other photos") {
                photographerProfile.getAlbum(newAlbumName).photos shouldContainAll photos.dropLast(2)
            }
        }
        When("Delete album") {
            photographerProfile.deleteAlbum(newAlbumName)
            Then("Profile don't has deleted album") {
                photographerProfile.albums.map { it.name } shouldNotContain newAlbumName
            }
            Then("Profile has all photos from album") {
                photographerProfile.getAllPhotos() shouldNotContain photos.dropLast(2)
            }
        }
    }
})