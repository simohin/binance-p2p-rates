package com.github.simohin.binance.p2p.rates.bot

import com.github.simohin.binance.p2p.rates.model.Asset
import com.github.simohin.binance.p2p.rates.model.FiatCurrency
import com.github.simohin.binance.p2p.rates.model.toTgMessage
import com.github.simohin.binance.p2p.rates.service.binance.BinanceService
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import kotlinx.coroutines.async
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import java.util.logging.Logger

@Configuration
class Schedule(
    private val bot: TgBot,
    private val scope: TgBotCoroutineScope,
    private val chatIds: TgBotChatIds,
    private val binance: BinanceService,
) {

    companion object {
        val log = Logger.getLogger(Schedule::class.simpleName)
    }

    @Async
    @Scheduled(fixedRateString = "\${tg.bot.rate.send.express.millis}")
    fun sendExpressRates() {
        if (chatIds.isEmpty()) return
        log.info("Sending express rates to ${chatIds.size} chats")
        binance.getExpressRates(setOf(Asset.USDT), FiatCurrency.RUB).subscribe {
            chatIds.forEach { chatId ->
                scope.async {
                    it.toTgMessage().forEach {
                        bot.sendMessage(chatId, it)
                    }
                }
            }
        }
    }

    @Async
    @Scheduled(fixedRateString = "\${tg.bot.rate.send.market.millis}")
    fun sendMarketRates() {
        if (chatIds.isEmpty()) return
        log.info("Sending market rates to ${chatIds.size} chats")
        binance.getMarketRates(Asset.USDT, FiatCurrency.RUB).subscribe {
            chatIds.forEach { chatId ->
                scope.async {
                    bot.sendMessage(chatId, it.toTgMessage())
                }
            }
        }
    }
}
