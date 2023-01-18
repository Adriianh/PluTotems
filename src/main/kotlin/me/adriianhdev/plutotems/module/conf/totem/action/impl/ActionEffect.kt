package me.adriianhdev.plutotems.module.conf.totem.action.impl

import org.bukkit.EntityEffect
import org.bukkit.entity.Player

object ActionEffect {
    fun execute(player: Player, effect: String) {
        player.playEffect(EntityEffect.valueOf(effect))
    }
}