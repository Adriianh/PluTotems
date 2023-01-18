package me.adriianhdev.plutotems.module.conf.totem.action.impl

import org.bukkit.entity.Player

object ActionFrozen {
    fun execute(player: Player, time: Int) {
        player.freezeTicks = 140 + (time * 20)
    }
}