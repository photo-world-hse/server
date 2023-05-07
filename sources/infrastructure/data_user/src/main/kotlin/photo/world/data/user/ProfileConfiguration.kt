package photo.world.data.user

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import photo.world.domain.profile.repository.AccountRepository
import photo.world.domain.profile.repository.ProfileRepository
import photo.world.domain.profile.repository.ServiceRepository
import photo.world.domain.profile.repository.TagRepository
import photo.world.domain.profile.service.AlbumService
import photo.world.domain.profile.service.ProfileContentService
import photo.world.domain.profile.service.ProfileService
import photo.world.domain.profile.service.TagAndServiceService
import photo.world.domain.profile.service.impl.DomainAlbumService
import photo.world.domain.profile.service.impl.DomainProfileContentService
import photo.world.domain.profile.service.impl.DomainProfileService
import photo.world.domain.profile.service.impl.DomainTagAndServiceService


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
}