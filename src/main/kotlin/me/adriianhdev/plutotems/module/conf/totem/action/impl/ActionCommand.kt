package me.adriianhdev.plutotems.module.conf.totem.action.impl

import org.bukkit.entity.Player

object ActionCommand {
    fun execute(player: Player, message: String) {
        player.performCommand(message.replace("{player}", player.name))
    }
}