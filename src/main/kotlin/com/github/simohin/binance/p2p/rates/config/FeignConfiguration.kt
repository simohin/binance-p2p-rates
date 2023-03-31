package com.github.simohin.binance.p2p.rates.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.codec.Decoder
import feign.codec.Encoder
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class FeignConfiguration {

    @Bean
    @Primary
    fun mapper(): ObjectMapper = jacksonObjectMapper()
        .apply {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        }

    @Bean
    fun decoder(mapper: ObjectMapper): Decoder = JacksonDecoder(mapper)

    @Bean
    fun encoder(mapper: ObjectMapper): Encoder = JacksonEncoder(mapper)
}
