package br.com.cleverton.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "message.hero")
class HeroValidationMessages {

    lateinit var notFound: String
    lateinit var nameInUse: String
}