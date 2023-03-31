package com.github.simohin.binance.p2p.rates.bot

enum class BotCommand {
    START,
    STOP;

    val value: String = name.lowercase()
}
