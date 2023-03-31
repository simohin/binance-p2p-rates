package com.github.simohin.binance.p2p.rates.service.binance

import com.github.simohin.binance.p2p.rates.dto.ExpressRateRequestDto
import com.github.simohin.binance.p2p.rates.dto.ExpressRatesResponseDto
import com.github.simohin.binance.p2p.rates.dto.SearchAdvRequestDto
import com.github.simohin.binance.p2p.rates.dto.SearchAdvResponseDto
import com.github.simohin.binance.p2p.rates.model.Asset
import com.github.simohin.binance.p2p.rates.model.ExpressRateMessage
import com.github.simohin.binance.p2p.rates.model.FiatCurrency
import com.github.simohin.binance.p2p.rates.model.MarketRateMessage
import com.github.simohin.binance.p2p.rates.model.TradeType
import com.github.simohin.binance.p2p.rates.service.binance.client.BinanceClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BinanceService(
    private val api: BinanceClient,
) {

    fun getExpressRates(assets: Collection<Asset>, fiatCurrency: FiatCurrency) =
        Mono.just(
            api.expressRates(
                ExpressRateRequestDto(
                    assets,
                    fiatCurrency,
                    TradeType.BUY
                )
            )
        ).flatMap { buy ->
            Mono.just(
                api.expressRates(
                    ExpressRateRequestDto(
                        assets,
                        fiatCurrency,
                        TradeType.SELL
                    )
                )
            ).map { sell -> buildExpressRateMessages(buy, sell) }
        }

    fun getMarketRates(asset: Asset, fiatCurrency: FiatCurrency) =
        Mono.just(
            api.marketRates(
                SearchAdvRequestDto(
                    asset,
                    fiatCurrency,
                    TradeType.BUY
                )
            )
        ).flatMap { buy ->
            Mono.just(
                api.marketRates(
                    SearchAdvRequestDto(
                        asset,
                        fiatCurrency,
                        TradeType.SELL
                    )
                )
            ).map { sell -> buildMarketRateMessages(asset, fiatCurrency, buy, sell) }
        }

    private fun buildMarketRateMessages(
        asset: Asset,
        fiatCurrency: FiatCurrency,
        buy: SearchAdvResponseDto,
        sell: SearchAdvResponseDto,
    ): MutableList<MarketRateMessage> {
        val buyRates = buy.data.map { it.adv }.iterator()
        val sellRates = sell.data.map { it.adv }.iterator()

        val messages = mutableListOf<MarketRateMessage>()
        while (buyRates.hasNext() && sellRates.hasNext()) {
            messages.add(
                MarketRateMessage(
                    asset,
                    fiatCurrency,
                    buyRates.next(),
                    sellRates.next()
                )
            )
        }
        return messages
    }

    private fun buildExpressRateMessages(buy: ExpressRatesResponseDto, sell: ExpressRatesResponseDto) =
        buy.data.map { buyRate ->
            ExpressRateMessage(
                buyRate.asset,
                buyRate.currency,
                buyRate.referencePrice,
                sell.data.first { sellRate ->
                    sellRate.asset == buyRate.asset
                        && sellRate.currency == buyRate.currency
                }.referencePrice
            )
        }
}
