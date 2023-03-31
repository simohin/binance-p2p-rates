package com.github.simohin.binance.p2p.rates.model

import dev.inmo.tgbotapi.types.message.textsources.TextSource
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.italic
import dev.inmo.tgbotapi.types.message.textsources.regularln
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class MarketRateMessage(
    val asset: Asset,
    val fiatCurrency: FiatCurrency,
    val buy: Adv,
    val sell: Adv,
)

fun Collection<MarketRateMessage>.toTgMessage(): MutableList<TextSource> {

    val result = mutableListOf(
        bold("Курсы топ 10 объявлений по паре ${this.first().asset}/${this.first().fiatCurrency}"),
        regularln(""),
        regularln(""),
        italic("Покупка:"),
        regularln(""),
    )

    this.map { it.buy }
        .map { regularln(it.price.toPlainString()) }
        .let { result.addAll(it) }

    result.addAll(
        listOf(
            regularln(""),
            italic("Продажа:"),
            regularln(""),
        )
    )
    this.map { it.sell }
        .map { regularln(it.price.toPlainString()) }
        .let { result.addAll(it) }

    result.addAll(
        listOf(
            regularln(""),
            regularln("Дата: ${OffsetDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))}"),
        )
    )

    return result
}
