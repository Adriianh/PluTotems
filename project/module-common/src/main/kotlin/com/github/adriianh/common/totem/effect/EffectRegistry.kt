package com.github.adriianh.common.totem.effect

import com.github.adriianh.common.totem.Totem
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect

object EffectRegistry {
    fun addPotionEffect(player: Player, effect: Effect) {
        player.addPotionEffect(
            PotionEffect(
                effect.type,
                effect.duration,
                effect.amplifier
            )
        )
    }

    fun Totem.addPotionsEffect(player: Player) {
        getEffects().forEach { effect ->
            player.addPotionEffect(
                PotionEffect(
                    effect.type,
                    effect.duration,
                    effect.amplifier
                )
            )
        }
    }

    fun Totem.removePotionsEffect(player: Player) {
        getEffects().forEach { effect ->
            player.removePotionEffect(effect.type)
        }
    }
}