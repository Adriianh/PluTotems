package me.adriianhdev.plutotems.module.conf.totem.action.impl

import org.bukkit.entity.Player

object ActionTellraw {
    fun execute(player: Player, message: String) {
        player.sendRawMessage(message)
    }
}