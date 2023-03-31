package com.github.simohin.binance.p2p.rates.dto

import com.github.simohin.binance.p2p.rates.model.AdvWrapper

data class SearchAdvResponseDto(
    val data: Collection<AdvWrapper>,
)
