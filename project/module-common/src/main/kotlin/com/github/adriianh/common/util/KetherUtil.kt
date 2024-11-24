package com.github.adriianh.common.util

import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.common.platform.function.warning
import taboolib.library.kether.LocalizedException
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture

object KetherUtil {
    fun eval(player: Player, script: String): CompletableFuture<Any?> {
        return try {
            KetherShell.eval(script, ScriptOptions.builder()
                .namespace(listOf("plutotems"))
                .sender(adaptPlayer(player))
                .build()
            )
        } catch (e: LocalizedException) {
            warning("§8[&6PluTotems&8] §8An error occurred while parsing kether shell:")
            e.localizedMessage.split("\n").forEach {
                warning("         §8$it")
            }
            CompletableFuture.completedFuture(false)
        }
    }

    fun List<String>.eval(player: Player) {
        try {
            forEach { script ->
                eval(player, script)
            }
        } catch (e: Throwable) {
            e.printKetherErrorMessage()
        }
    }

    fun ScriptFrame.getBukkitPlayer(): Player {
        return script().sender?.castSafely<Player>() ?: error("No player selected.")
    }
}