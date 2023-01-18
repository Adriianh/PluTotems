package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import org.bukkit.entity.Player

object ActionBroadcast {
    fun execute(player: Player, message: String) {
        player.server.broadcastMessage(message
            .replace("{player}", player.name)
            .colorify()
        )
    }
}