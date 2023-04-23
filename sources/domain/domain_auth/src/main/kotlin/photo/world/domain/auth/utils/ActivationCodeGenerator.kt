package photo.world.domain.auth.utils

import kotlin.random.Random
import kotlin.random.nextInt

object ActivationCodeGenerator {

    fun generateNewCode(): String {
        val code = Random(System.currentTimeMillis()).nextInt(0..999999)
        return code.toString().padStart(6, '0')
    }
}