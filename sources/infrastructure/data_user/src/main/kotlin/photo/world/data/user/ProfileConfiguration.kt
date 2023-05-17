package photo.world.data.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import photo.world.domain.profile.repository.AccountRepository
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.repository.ServiceRepository
import photo.world.domain.profile.repository.TagRepository
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
    ): ProfileService =
        DomainProfileService(
            accountRepository,
            profileRepository,
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
}