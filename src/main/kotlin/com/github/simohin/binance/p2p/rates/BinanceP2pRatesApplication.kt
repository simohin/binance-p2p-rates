package com.github.simohin.binance.p2p.rates

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableAsync
class BinanceP2pRatesApplication

fun main(args: Array<String>) {
    runApplication<BinanceP2pRatesApplication>(*args)
}
