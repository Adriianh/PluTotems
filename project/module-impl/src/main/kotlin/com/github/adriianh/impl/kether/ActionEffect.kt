package com.github.adriianh.impl.kether

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import taboolib.library.kether.ParsedAction
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture

class Give(
    val name: ParsedAction<*>,
    val duration: ParsedAction<*>,
    val amplifier: ParsedAction<*>
) : ScriptAction<Void>() {
    override fun run(frame: ScriptFrame): CompletableFuture<Void> {
        val player = frame.script().sender?.castSafely<Player>() ?: error("No player found.")

        frame.run(name).str { effect ->
            frame.run(duration).int { duration ->
                frame.run(amplifier).int { amplifier ->
                    val effectType = PotionEffectType.getByName(effect) ?: error("Unknown effect: $effect")
                    player.addPotionEffect(effectType.createEffect(duration, amplifier))
                }
            }
        }
        return CompletableFuture.completedFuture(null)
    }
}

class Remove(val name: ParsedAction<*>) : ScriptAction<Void>() {
    override fun run(frame: ScriptFrame): CompletableFuture<Void> {
        val player = frame.script().sender?.castSafely<Player>() ?: error("No player found.")

        frame.run(name).str { effect ->
            val effectType = PotionEffectType.getByName(effect) ?: error("Unknown effect: $effect")
            player.removePotionEffect(effectType)
        }
        return CompletableFuture.completedFuture(null)
    }
}

class Clear : ScriptAction<Void>() {
    override fun run(frame: ScriptFrame): CompletableFuture<Void> {
        val player = frame.script().sender?.castSafely<Player>() ?: error("No player found.")
        player.activePotionEffects.forEach { player.removePotionEffect(it.type) }
        return CompletableFuture.completedFuture(null)
    }
}

/*
*   effect give <effect> <duration> <amplifier>
*   effect remove <effect>
*   effect clear
* */
@KetherParser(["effect"], namespace = "plutotems", shared = true)
private fun parser() = scriptParser {
    it.switch {
        case("give") {
            Give(it.nextParsedAction(), it.nextParsedAction(), it.nextParsedAction())
        }
        case("remove") {
            Remove(it.nextParsedAction())
        }
        case("clear") {
            Clear()
        }
    }
}