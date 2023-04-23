package photo.world.domain.mail

interface EmailService {

    fun sendMessage(
        username: String,
        userEmail: String,
        activationCode: String,
    )

    companion object {

        private const val WelcomeMessageWithActivationCode =
            "%s welcome to Photo World!\nYour activation code: %s"
        const val EmailSubject = "PhotoWorld activation code"

        fun createWelcomeMessage(
            userName: String,
            activationCode: String,
        ) = WelcomeMessageWithActivationCode.format(userName, activationCode)
    }
}