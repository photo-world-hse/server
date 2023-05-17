package photo.world.infrastructure.sendbird

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import photo.world.domain.auth.service.ChatUserCreatorService
import photo.world.domain.photosession.service.ChatService
import photo.world.domain.profile.service.ChatProfileService
import photo.world.infrastructure.sendbird.dto.request.ChangeUserDataDto
import photo.world.infrastructure.sendbird.dto.request.CreateChatDto
import photo.world.infrastructure.sendbird.dto.request.CreateUserDto
import photo.world.infrastructure.sendbird.dto.request.JoinToChatDto
import photo.world.infrastructure.sendbird.dto.response.AccessTokenDto
import reactor.core.publisher.Mono

@Component
class ChatServiceImpl(
    @Qualifier("sendbirdClient")
    private val sendbirdClient: WebClient,
) : ChatUserCreatorService, ChatService, ChatProfileService {

    override fun createChatUserForUser(
        id: String,
        name: String,
    ): String? =
        sendbirdClient.post()
            .uri(CreateUserUri)
            .body(
                Mono.just(CreateUserDto(userID = id, nickname = name)),
                CreateUserDto::class.java,
            )
            .retrieve()
            .toEntity(AccessTokenDto::class.java)
            .toFuture()
            .get()
            .body
            ?.accessToken

    override fun createChat(
        photosessionName: String,
        userId: String,
        chatUrl: String,
    ) {
        createChat(
            chatName = photosessionName,
            chatUrl = chatUrl,
            userIds = listOf(userId),
        )
    }

    override fun addToChat(
        chatUrl: String,
        userId: String,
    ) {
        sendbirdClient.put()
            .uri(addUserToChatUri(chatUrl))
            .body(
                Mono.just(JoinToChatDto(userId = userId)),
                JoinToChatDto::class.java,
            )
            .retrieve()
            .bodyToMono<String>()
            .subscribe()
    }

    override fun setProfileImage(userId: String, avatarUrl: String?) {
        sendbirdClient.put()
            .uri(changeUserDataUri(userId))
            .body(
                Mono.just(ChangeUserDataDto(profileUrl = avatarUrl.orEmpty())),
                ChangeUserDataDto::class.java,
            )
            .retrieve()
            .bodyToMono<String>()
            .subscribe()
    }

    override fun createPrivateChatForUsers(
        chatUrl: String,
        chatName: String,
        initializerUserId: String,
        participantUserId: String,
    ) {
        createChat(
            chatName = chatName,
            chatUrl = chatUrl,
            userIds = listOf(
                initializerUserId,
                participantUserId,
            ),
        )
    }

    private fun createChat(
        chatName: String,
        chatUrl: String,
        userIds: List<String>,
    ) {
        sendbirdClient.post()
            .uri(CreateChatUri)
            .body(
                Mono.just(CreateChatDto(chatName = chatName, channelUrl = chatUrl, userIds = userIds)),
                CreateChatDto::class.java,
            )
            .retrieve()
            .bodyToMono<Unit>()
            .subscribe()
    }

    private companion object {

        const val ApiVersion = "v3"

        const val CreateUserUri = "/$ApiVersion/users"
        const val CreateChatUri = "/$ApiVersion/group_channels"
        const val AddUserToChatUri = "/$ApiVersion/group_channels/%s/join"
        const val ChangeUserDataUri = "/$ApiVersion/users/%s"

        fun addUserToChatUri(chatUrl: String) = AddUserToChatUri.format(chatUrl)

        fun changeUserDataUri(userId: String) = ChangeUserDataUri.format(userId)
    }
}