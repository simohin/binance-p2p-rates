package com.github.simohin.binance.p2p.rates.bot

import dev.inmo.tgbotapi.bot.TelegramBot
import dev.inmo.tgbotapi.bot.ktor.telegramBot
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onCommand
import dev.inmo.tgbotapi.types.IdChatIdentifier
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

typealias TgBotCoroutineScope = CoroutineScope
typealias TgBot = TelegramBot
typealias TgBotChatIds = MutableSet<IdChatIdentifier>

@Configuration
class BotConfig(
    @Value("\${tg.bot.token}")
    private val token: String,
) {

    private val scope: TgBotCoroutineScope = CoroutineScope(Dispatchers.Default)
    private val chats: TgBotChatIds = mutableSetOf()
    private val bot: TgBot = telegramBot(token)

    @PostConstruct
    fun init() = scope.async {
        bot.buildBehaviourWithLongPolling {
            onCommand(BotCommand.START.value) { onStart(it) }
            onCommand(BotCommand.STOP.value) { onStop(it) }
        }.join()
    }

    @Bean
    fun chatIds() = chats

    @Bean
    fun bot() = bot

    @Bean
    fun scope() = scope

    private fun onStart(msg: CommonMessage<TextContent>) {
        msg.chat.id.let(chats::add)
        TODO("Not yet implemented")
    }

    private fun onStop(msg: CommonMessage<TextContent>) {
        msg.chat.id.let(chats::remove)
        TODO("Not yet implemented")
    }
}
