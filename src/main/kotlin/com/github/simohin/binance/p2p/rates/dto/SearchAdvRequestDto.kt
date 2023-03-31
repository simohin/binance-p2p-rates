package com.github.simohin.binance.p2p.rates.dto

import com.github.simohin.binance.p2p.rates.model.Asset
import com.github.simohin.binance.p2p.rates.model.FiatCurrency
import com.github.simohin.binance.p2p.rates.model.TradeType

data class SearchAdvRequestDto(
    val asset: Asset,
    val fiat: FiatCurrency,
    val tradeType: TradeType,
    val page: Int = 1,
    val rows: Int = 10,
)
