package photo.world.web.images

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import photo.world.domain.images.ImageService
import photo.world.web.images.dto.DeleteFileRequestDto
import photo.world.web.images.dto.UploadFileResponse
import java.io.File
import java.io.FileOutputStream
import java.util.*

@RestController
@RequestMapping("/api/v1/images")
internal class ImageController(
    private val imageService: ImageService,
) {

    @PostMapping("/upload")
    fun uploadFile(
        @RequestPart("file") file: MultipartFile,
        authentication: Authentication,
    ): ResponseEntity<UploadFileResponse> {
        val imageUrl = imageService.uploadImage(
            file = file.toFile(),
            email = authentication.name
        )
        return ResponseEntity.ok(UploadFileResponse(imageUrl))
    }

    @DeleteMapping("/delete")
    fun deleteFile(
        @RequestBody request: DeleteFileRequestDto,
        authentication: Authentication,
    ): ResponseEntity<Unit> {
        val fileName = request.imageUrl.split("/").last()
        val imageEmail = fileName.split("-").first()
        if (imageEmail != authentication.name) return ResponseEntity(HttpStatus.FORBIDDEN)
        imageService.deleteImage(fileName)
        return ResponseEntity.ok().build()
    }

    private fun MultipartFile.toFile(): File {
        val fileName = originalFilename ?: "file-${Date().time}"
        val convFile = File(fileName)
        val fos = FileOutputStream(convFile)
        fos.write(bytes)
        fos.close()
        return convFile
    }
}