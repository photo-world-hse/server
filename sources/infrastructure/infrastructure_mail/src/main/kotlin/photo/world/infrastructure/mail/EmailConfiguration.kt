package photo.world.infrastructure.mail

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

private const val Host = "smtp.gmail.com"
private const val EmailPort = 587
private const val EmailForSending = "pure.mind.activation.code@gmail.com"
private const val EmailPassword = "pjglidbninffcbpg"

@Configuration
class EmailConfiguration {

    @Bean
    fun getJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = Host
        mailSender.port = EmailPort

        mailSender.username = EmailForSending
        mailSender.password = EmailPassword

        val props: Properties = mailSender.javaMailProperties
        props["mail.transport.protocol"] = "smtp"
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.debug"] = "true"
        props["X-Priority"] = "1"

        return mailSender
    }
}