package photo.world.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {

    @Value("\${sendbird.appId}")
    private lateinit var appId: String

    @Value("\${sendbird.apiToken}")
    private lateinit var apiToken: String

    @Bean("sendbirdClient")
    fun sendbirdClient(): WebClient = WebClient.builder()
        .baseUrl("https://api-$appId.sendbird.com")
        .defaultHeader("Api-Token", apiToken)
        .build()
}