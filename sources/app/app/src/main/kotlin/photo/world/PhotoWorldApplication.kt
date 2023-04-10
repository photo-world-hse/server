package photo.world

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class PhotoWorldApplication

fun main(args: Array<String>) {
    runApplication<PhotoWorldApplication>(*args)
}
