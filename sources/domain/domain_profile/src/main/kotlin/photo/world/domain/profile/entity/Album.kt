package photo.world.domain.profile.entity

data class Album(
    val name: String,
    val photos: MutableList<String>,
)