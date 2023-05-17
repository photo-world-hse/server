package photo.world.domain.profile.service.impl

import photo.world.common.profile.ProfileType
import photo.world.domain.profile.entity.profile.Profile
import photo.world.domain.profile.repository.AccountRepository
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.repository.UserChatRepository
import photo.world.domain.profile.service.ChatProfileService
import photo.world.domain.profile.service.ProfileService
import photo.world.domain.profile.service.data.ProfileData

class DomainProfileService(
    private val accountRepository: AccountRepository,
    private val profileRepository: ProfileRepository,
    private val userChatRepository: UserChatRepository,
    private val chatProfileService: ChatProfileService,
) : ProfileService {

    override fun createNewProfile(
        accountEmail: String,
        profileData: ProfileData,
    ): Profile {
        val account = accountRepository.getAccount(accountEmail)
        val profile = account.addProfile(profileData) { profile ->
            profile.avatarUrl?.let { avatarUrl ->
                chatProfileService.setProfileImage(
                    userId = account.chatAccountId,
                    avatarUrl = avatarUrl,
                )
            }
        }
        profileRepository.saveNew(accountEmail, profile)
        return profile
    }

    override fun deleteProfile(accountEmail: String, profileType: ProfileType) {
        val account = accountRepository.getAccount(accountEmail)
        val profile = account.deleteProfile(profileType) { profile ->
            chatProfileService.setProfileImage(
                userId = account.chatAccountId,
                avatarUrl = profile?.avatarUrl,
            )
        }
        accountRepository.update(account)
        profileRepository.delete(accountEmail, profile)
    }

    override fun getPersonalChatWithUser(initializerEmail: String, participantEmail: String): String =
        userChatRepository.getChatUrlForUsers(initializerEmail, participantEmail)
            ?: createNewChat(initializerEmail, participantEmail)

    private fun createNewChat(initializerEmail: String, participantEmail: String): String {
        val initializerAccount = accountRepository.getAccount(initializerEmail)
        val participantAccount = accountRepository.getAccount(participantEmail)
        val chatUrl = "${initializerAccount.chatAccountId}_${participantAccount.chatAccountId}"
        chatProfileService.createPrivateChatForUsers(
            chatUrl = chatUrl,
            chatName = "${initializerAccount.name}, ${participantAccount.name}",
            initializerUserId = initializerAccount.chatAccountId,
            participantUserId = participantAccount.chatAccountId,
        )
        userChatRepository.saveNewChat(initializerEmail, participantEmail, chatUrl)
        return chatUrl
    }
}