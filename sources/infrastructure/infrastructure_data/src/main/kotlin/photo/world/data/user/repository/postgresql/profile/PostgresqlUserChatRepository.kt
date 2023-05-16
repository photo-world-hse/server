package photo.world.data.user.repository.postgresql.profile

import org.springframework.stereotype.Component
import photo.world.data.user.entity.profile.UsersChatRelationship
import photo.world.data.user.repository.spring.profile.SpringDataPostgresqlUserChatRepository
import photo.world.domain.profile.repository.UserChatRepository

@Component
class PostgresqlUserChatRepository(
    private val springDataPostgresqlUserChatRepository: SpringDataPostgresqlUserChatRepository,
) : UserChatRepository {

    override fun getChatUrlForUsers(userEmail1: String, userEmail2: String): String? =
        springDataPostgresqlUserChatRepository.findByUserIds(userEmail1, userEmail2)?.chatId

    override fun saveNewChat(userEmail1: String, userEmail2: String, chatId: String) {
        val usersChatRelationship = UsersChatRelationship(
            chatId = chatId,
            userEmail1 = userEmail1,
            userEmail2 = userEmail2,
        )
        springDataPostgresqlUserChatRepository.save(usersChatRelationship)
    }
}