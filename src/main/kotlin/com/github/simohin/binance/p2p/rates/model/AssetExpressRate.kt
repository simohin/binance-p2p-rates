package com.github.simohin.binance.p2p.rates.model

import java.math.BigDecimal

data class AssetExpressRate(
    val asset: Asset,
    val currency: FiatCurrency,
    val currencyScale: Int,
    val currencySymbol: Char,
    val referencePrice: BigDecimal,
    val assetScale: Int,
    val priceScale: Int,
)
