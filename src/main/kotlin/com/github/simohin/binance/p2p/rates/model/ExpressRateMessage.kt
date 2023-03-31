package com.github.simohin.binance.p2p.rates.model

import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.italic
import dev.inmo.tgbotapi.types.message.textsources.regularln
import java.math.BigDecimal
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class ExpressRateMessage(
    val asset: Asset,
    val fiatCurrency: FiatCurrency,
    val buyPrice: BigDecimal,
    val sellPrice: BigDecimal,
)

fun Collection<ExpressRateMessage>.toTgMessage() = map {
    listOf(
        bold("Текущий курс по паре ${it.asset}/${it.fiatCurrency}"),
        regularln(""),
        regularln(""),
        italic("Покупка:"),
        regularln(" ${it.buyPrice}"),
        italic("Покупка:"),
        regularln(" ${it.sellPrice}"),
        regularln(""),
        regularln("Дата: ${OffsetDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))}"),
    )
}
