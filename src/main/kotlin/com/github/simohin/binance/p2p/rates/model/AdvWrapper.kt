package com.github.simohin.binance.p2p.rates.model

import java.math.BigDecimal

data class AdvWrapper(
    val adv: Adv,
)

data class Adv(
    val price: BigDecimal,
    val asset: Asset,
    val fiatUnit: FiatCurrency,
    val tradeType: TradeType,
)
