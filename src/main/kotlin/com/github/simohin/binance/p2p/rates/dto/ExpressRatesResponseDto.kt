package com.github.simohin.binance.p2p.rates.dto

import com.github.simohin.binance.p2p.rates.model.AssetExpressRate

data class ExpressRatesResponseDto(
    val data: Collection<AssetExpressRate>,
)
