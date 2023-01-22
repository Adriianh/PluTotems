package me.adriianhdev.plutotems.module.conf.totem.effect

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Effect {
    fun addEffect(text: String, player: Player) {
        val effect = text.split(":")[0]
        val level = text.split(":")[1]
        val duration = text.split(":")[2]

        player.addPotionEffect(
            PotionEffect(
                PotionEffectType.getByName(effect)!!,
                duration.toInt(),
                level.toInt()
            )
        )
    }

    fun removeEffect(text: String, player: Player) {
        val effect = text.split(":")[0]
        if (!player.hasPotionEffect(PotionEffectType.getByName(effect)!!)) return

        player.removePotionEffect(
            PotionEffectType.getByName(effect)!!
        )
    }
}