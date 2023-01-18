package me.adriianhdev.plutotems.module.conf.totem.action.impl

import org.bukkit.entity.Player

object ActionFire {
    fun execute(player: Player, time: Int) {
        player.fireTicks = 140 + (time * 20)
    }
}