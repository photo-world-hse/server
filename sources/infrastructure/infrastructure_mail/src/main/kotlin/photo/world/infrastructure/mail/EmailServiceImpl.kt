package photo.world.infrastructure.mail

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import photo.world.domain.mail.EmailService

@Service
class EmailServiceImpl @Autowired constructor(
    private val javaMailSender: JavaMailSender,
): EmailService {

    override fun sendMessage(
        username: String,
        userEmail: String,
        activationCode: String,
    ) {
        val message = SimpleMailMessage().apply {
            from = "noreply@baeldung.com"
            setTo(userEmail)
            subject = EmailService.EmailSubject
            text = EmailService.createWelcomeMessage(username, activationCode)
        }
        javaMailSender.send(message)
    }
}