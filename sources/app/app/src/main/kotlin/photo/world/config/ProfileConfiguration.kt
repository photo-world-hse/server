package photo.world.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import photo.world.domain.profile.repository.*
import photo.world.domain.profile.service.*
import photo.world.domain.profile.service.impl.*


@Configuration
class ProfileConfiguration {

    @Bean
    fun albumService(
        accountRepository: AccountRepository,
        profileRepository: ProfileRepository,
    ): AlbumService =
        DomainAlbumService(
            accountRepository,
            profileRepository,
        )


    @Bean
    fun profileService(
        accountRepository: AccountRepository,
        profileRepository: ProfileRepository,
        chatProfileService: ChatProfileService,
        userChatRepository: UserChatRepository,
    ): ProfileService =
        DomainProfileService(
            accountRepository = accountRepository,
            profileRepository = profileRepository,
            userChatRepository = userChatRepository,
            chatProfileService = chatProfileService,
        )

    @Bean
    fun profileContentService(
        accountRepository: AccountRepository,
        profileRepository: ProfileRepository,
    ): ProfileContentService =
        DomainProfileContentService(
            accountRepository,
            profileRepository,
        )

    @Bean
    fun tagAndServiceService(
        tagRepository: TagRepository,
        serviceRepository: ServiceRepository,
    ): TagAndServiceService =
        DomainTagAndServiceService(
            tagRepository,
            serviceRepository,
        )

    @Bean
    fun searchService(profileRepository: ProfileRepository): SearchService =
        DomainSearchService(profileRepository)

    @Bean
    fun commentService(
        accountRepository: AccountRepository,
        profileRepository: ProfileRepository,
    ): CommentService =
        DomainCommentsService(
            accountRepository,
            profileRepository,
        )
}