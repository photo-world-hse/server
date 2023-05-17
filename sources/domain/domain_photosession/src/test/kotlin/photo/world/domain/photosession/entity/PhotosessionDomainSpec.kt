package photo.world.domain.photosession.entity

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import photo.world.common.profile.ProfileType
import photo.world.domain.errors.DomainException
import java.time.Instant
import java.util.*

class PhotosessionDomainSpec : BehaviorSpec({
    val photosessionData = PhotosessionData(
        name = "Test name1",
        description = "test description1",
        duration = 2.5f,
        address = "test address1",
        startDateAndTime = Date.from(Instant.ofEpochSecond(0)),
    )
    val photosession = Photosession.createPhotosession(
        organizerData = ProfileData(
            email = "test@email.ru",
            name = "Roman Nazmutdinov",
            profileType = ProfileType.MODEL,
            avatarUrl = "",
            rating = 5.0f,
            commentsNumber = 10,
        ),
        photosessionData = photosessionData,
    )

    Given("Photosession") {
        When("Photosession was created") {
            Then("Photosession should contain invited organiser") {
                photosession.organizer shouldBe PhotosessionProfile(
                    email = "test@email.ru",
                    name = "Roman Nazmutdinov",
                    profileType = ProfileType.MODEL,
                    inviteStatus = InviteStatus.READY,
                    avatarUrl = "",
                    rating = 5.0f,
                    commentsNumber = 10,
                )
            }
            Then("Photosession should not contain photos") {
                photosession.photos.isEmpty() shouldBe true
            }
            Then("Photosession should be equal to the passed data") {
                photosession.photosessionData shouldBe photosessionData
            }
            Then("Photosession should not has participants") {
                photosession.participants.isEmpty() shouldBe true
            }
            Then("Photosession should not has result photos") {
                photosession.resultPhotos.isEmpty() shouldBe true
            }
        }

        val photos = listOf(
            "test photo 1",
            "test photo 2",
            "test photo 3",
            "test photo 4",
            "test photo 5",
        )
        When("Add photos") {
            photosession.addPhotos(photos)
            Then("Photosession should contain all added photos") {
                photosession.photos shouldContainExactly photos
            }
        }
        val deletedPhotos = listOf(
            "test photo 4",
            "test photo 5",
        )
        When("Delete photos") {
            photosession.removePhotos(deletedPhotos)
            Then("Photosession should not contain deleted photos") {
                val existingPhotos = photos.subtract(deletedPhotos)
                photosession.photos shouldNotContain deletedPhotos
                photosession.photos shouldContainExactly existingPhotos
            }
        }
        val notExistingPhotos = listOf(
            "test photo 6",
            "test photo 7",
        )
        When("Delete not existing photo") {
            Then("Thrown domain exception") {
                shouldThrow<DomainException> {
                    photosession.removePhotos(notExistingPhotos)
                }
            }
        }

        val newPhotosessionData = PhotosessionData(
            name = "Test name2",
            description = "test description2",
            duration = 1.5f,
            address = "test address2",
            startDateAndTime = Date.from(Instant.ofEpochSecond(10)),
        )
        When("Edit photosession data") {
            photosession.edit(newPhotosessionData)
            Then("Photosession has new data") {
                photosession.photosessionData shouldBe newPhotosessionData
            }
        }

        val profileData = ProfileData(
            email = "profile@test1.com",
            name = "Test user 1",
            profileType = ProfileType.MODEL,
            avatarUrl = "",
            rating = 5.0f,
            commentsNumber = 10,
        )
        When("Invite new profile") {
            photosession.invite(profileData)
            Then("Add profile to photosession and has pending status") {
                val profileWithInvite = PhotosessionProfile(
                    email = profileData.email,
                    name = profileData.name,
                    profileType = profileData.profileType,
                    inviteStatus = InviteStatus.PENDING,
                    avatarUrl = "",
                    rating = 5.0f,
                    commentsNumber = 10,
                )
                photosession.participants shouldContainExactly listOf(profileWithInvite)
            }
        }
        When("Accept invite from profile") {
            photosession.acceptInvitationBy(
                email = profileData.email,
                profileType = profileData.profileType,
            )
            Then("Profile inviteStatus was changed") {
                val profileWithAcceptInviteStatus = PhotosessionProfile(
                    email = profileData.email,
                    name = profileData.name,
                    profileType = profileData.profileType,
                    inviteStatus = InviteStatus.READY,
                    avatarUrl = "",
                    rating = 5.0f,
                    commentsNumber = 10,
                )
                photosession.participants shouldContainExactly listOf(profileWithAcceptInviteStatus)
            }
        }
        When("Remove accepted profile") {
            photosession.removeParticipant(
                email = profileData.email,
                profileType = profileData.profileType,
            )
            Then("Profile should be removed") {
                photosession.participants.isEmpty() shouldBe true
            }
        }

        val profiles = listOf(
            ProfileData(
                email = "profile@test2.com",
                name = "Test user 2",
                profileType = ProfileType.VISAGIST,
                avatarUrl = "",
                rating = 5.0f,
                commentsNumber = 10,
            ),
            ProfileData(
                email = "profile@test3.com",
                name = "Test user 3",
                profileType = ProfileType.VISAGIST,
                avatarUrl = "",
                rating = 5.0f,
                commentsNumber = 10,
            ),
        )
        When("Invite several profiles") {
            photosession.inviteAll(profiles)
            Then("Profiles added and has ready invite status") {
                photosession.participants.forEachIndexed { index, elem ->
                    elem.email shouldBe profiles[index].email
                    elem.name shouldBe profiles[index].name
                    elem.profileType shouldBe profiles[index].profileType
                    elem.inviteStatus shouldBe InviteStatus.PENDING
                }
            }
        }
        val testProfile2 = profiles[0]
        When("Cancel invitation") {
            photosession.cancelInvitationBy(
                email = testProfile2.email,
                profileType = testProfile2.profileType,
            )
            Then("Profile should has canceled invite status") {
                val participant = photosession.participants.find {
                    it.email == testProfile2.email && it.profileType == testProfile2.profileType
                }
                participant?.inviteStatus shouldBe InviteStatus.CANCELED
            }
        }
        val testProfile3 = profiles[1]
        When("Remove canceled profile") {
            photosession.removeParticipant(
                email = testProfile2.email,
                profileType = testProfile2.profileType,
            )
            Then("Profile removed") {
                photosession.participants.size shouldBe 1
                val participant = photosession.participants.find {
                    it.email == testProfile2.email && it.profileType == testProfile2.profileType
                }
                participant shouldBe null
            }
            Then("Try change invite status of removed participant") {
                shouldThrow<DomainException> {
                    photosession.acceptInvitationBy(testProfile2.email, testProfile2.profileType)
                }
            }
        }
        When("Remove pending profile") {
            photosession.removeParticipant(
                email = testProfile3.email,
                profileType = testProfile3.profileType,
            )
            Then("Profile removed") {
                photosession.participants.isEmpty() shouldBe true
                val participant = photosession.participants.find {
                    it.email == testProfile3.email && it.profileType == testProfile3.profileType
                }
                participant shouldBe null
            }
            Then("Try change invite status of removed participant") {
                shouldThrow<DomainException> {
                    photosession.acceptInvitationBy(testProfile3.email, testProfile3.profileType)
                }
            }
        }
        When("Remove not existing profile") {
            Then("Thrown domainException") {
                shouldThrow<DomainException> {
                    photosession.removeParticipant(
                        email = testProfile2.email,
                        profileType = testProfile2.profileType,
                    )
                }
            }
        }

        val tags = listOf(
            "tag 1",
            "tag 2",
            "tag 3",
        )
        When("Change tags") {
            photosession.setTags(tags)
            Then("Tags changed") {
                photosession.tags shouldContainExactly tags
            }
        }
        When("Delete tags") {
            photosession.setTags(listOf())
            Then("Tags was deleted") {
                photosession.tags.isEmpty() shouldBe true
            }
        }

        val resultPhotos = listOf(
            "result photo 1",
            "result photo 2",
            "result photo 3",
        )
        When("Finish photosession") {
            photosession.finish(resultPhotos)
            Then("Photosession finished") {
                photosession.isFinished shouldBe true
            }
            Then("Photosession has result photos") {
                photosession.resultPhotos shouldContainExactly resultPhotos
            }
        }
    }
})
