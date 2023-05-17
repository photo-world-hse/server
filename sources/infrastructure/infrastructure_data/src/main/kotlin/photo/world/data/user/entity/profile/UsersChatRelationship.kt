package photo.world.data.user.entity.profile

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users_chat")
data class UsersChatRelationship(
    @Id
    @Column(name = "users_chat_id")
    val chatId: String,
    val userEmail1: String,
    val userEmail2: String,
)