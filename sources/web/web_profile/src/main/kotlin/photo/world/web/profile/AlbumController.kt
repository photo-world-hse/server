package photo.world.web.profile

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import photo.world.domain.errors.withValidation
import photo.world.domain.profile.service.AlbumService
import photo.world.web.profile.dto.AlbumDto
import photo.world.web.profile.dto.request.ChangeAlbumNameRequestDto
import photo.world.web.profile.dto.request.CreateAlbumRequestDto
import photo.world.web.profile.dto.request.ChangeAlbumPhotosRequestDto
import photo.world.web.profile.dto.response.GetAlbumsResponseDto
import photo.world.web.profile.dto.response.GetPhotosResponseDto
import photo.world.web.profile.utils.ProfileValidator
import photo.world.web.profile.utils.getProfileTypeByName

@RestController
@RequestMapping("/api/v1/profiles/{profileName}/albums")
class AlbumController(
    val albumService: AlbumService,
) {

    @GetMapping
    fun getAlbums(
        @PathVariable profileName: String,
        authentication: Authentication,
    ): ResponseEntity<GetAlbumsResponseDto> {
        val profileType = getProfileTypeByName(profileName)
        val albums = albumService.getAlbums(
            accountEmail = authentication.name,
            profileType = profileType
        )
        return ResponseEntity.ok(
            GetAlbumsResponseDto(
                albums = albums.map { album ->
                    AlbumDto(
                        name = album.name,
                        firstImageUrl = album.photos.firstOrNull(),
                        photoNumber = album.photos.size,
                        isPrivate = album.isPrivate,
                    )
                }
            )
        )
    }

    @GetMapping("/{albumName}")
    fun getAlbum(
        @PathVariable profileName: String,
        @PathVariable albumName: String,
        authentication: Authentication,
    ): ResponseEntity<GetPhotosResponseDto> {
        val profileType = getProfileTypeByName(profileName)
        val album = albumService.getAlbum(
            accountEmail = authentication.name,
            profileType = profileType,
            albumName = albumName,
        )
        return ResponseEntity.ok(GetPhotosResponseDto(album.photos))
    }

    @PutMapping
    fun createAlbum(
        @PathVariable profileName: String,
        @RequestBody request: CreateAlbumRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> =
        withValidation(request, ProfileValidator::validateCreateAlbumRequestDto) {
            val profileType = getProfileTypeByName(profileName)
            albumService.addAlbum(
                accountEmail = authentication.name,
                profileType = profileType,
                albumName = request.albumName,
                photos = request.photos,
                isPrivate = request.isPrivate,
            )
            return ResponseEntity.ok().build()
        }

    @PostMapping("/{albumName}")
    private fun changeAlbumName(
        @PathVariable profileName: String,
        @PathVariable albumName: String,
        @RequestBody request: ChangeAlbumNameRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        albumService.changeAlbumName(
            accountEmail = authentication.name,
            profileType = profileType,
            albumName = albumName,
            newAlbumName = request.newName,
        )
        return ResponseEntity.ok().build()
    }

    @PutMapping("/{albumName}/photos")
    private fun addNewPhotosToAlbum(
        @PathVariable profileName: String,
        @PathVariable albumName: String,
        @RequestBody request: ChangeAlbumPhotosRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        albumService.addPhotosToAlbum(
            accountEmail = authentication.name,
            profileType = profileType,
            albumName = albumName,
            photos = request.photos,
        )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{albumName}/photos")
    private fun deletePhotosFromAlbum(
        @PathVariable profileName: String,
        @PathVariable albumName: String,
        @RequestBody request: ChangeAlbumPhotosRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        albumService.deletePhotosFromAlbum(
            accountEmail = authentication.name,
            profileType = profileType,
            albumName = albumName,
            photos = request.photos,
        )
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{albumName}")
    private fun deleteAlbum(
        @PathVariable profileName: String,
        @PathVariable albumName: String,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val profileType = getProfileTypeByName(profileName)
        albumService.deleteAlbum(
            accountEmail = authentication.name,
            profileType = profileType,
            albumName = albumName,
        )
        return ResponseEntity.ok().build()
    }
}