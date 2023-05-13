package photo.world.domain.photosession.entity

import java.util.Date

data class PhotosessionData(
    val name: String,
    val description: String,
    val duration: Float,
    val address: String,
    val startDateAndTime: Date,
)
