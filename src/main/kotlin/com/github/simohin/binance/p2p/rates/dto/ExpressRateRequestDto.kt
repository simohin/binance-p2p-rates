package com.github.simohin.binance.p2p.rates.dto

import com.github.simohin.binance.p2p.rates.model.Asset
import com.github.simohin.binance.p2p.rates.model.FiatCurrency
import com.github.simohin.binance.p2p.rates.model.TradeType
import com.github.simohin.binance.p2p.rates.model.UserRole

data class ExpressRateRequestDto(
    val assets: Collection<Asset>,
    val fiatCurrency: FiatCurrency,
    val tradeType: TradeType,
    val fromUserRole: UserRole = UserRole.USER,
)
