package com.github.simohin.binance.p2p.rates.service.binance.client

import com.github.simohin.binance.p2p.rates.config.FeignConfiguration
import com.github.simohin.binance.p2p.rates.dto.ExpressRateRequestDto
import com.github.simohin.binance.p2p.rates.dto.ExpressRatesResponseDto
import com.github.simohin.binance.p2p.rates.dto.SearchAdvRequestDto
import com.github.simohin.binance.p2p.rates.dto.SearchAdvResponseDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient("rates", url = "\${binance.url}", configuration = [FeignConfiguration::class])
interface BinanceClient {
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/bapi/c2c/v2/public/c2c/adv/quoted-price"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun expressRates(dto: ExpressRateRequestDto): ExpressRatesResponseDto

    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/bapi/c2c/v2/friendly/c2c/adv/search"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun marketRates(dto: SearchAdvRequestDto): SearchAdvResponseDto
}
