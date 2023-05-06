package photo.world.domain.images

import java.io.File

interface ImageService {

    fun uploadImage(file: File, email: String): String

    fun deleteImage(fileName: String)
}