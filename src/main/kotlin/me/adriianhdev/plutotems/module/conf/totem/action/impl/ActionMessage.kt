package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import org.bukkit.entity.Player

object ActionMessage {
    fun execute(player: Player, message: String) {
        player.sendMessage(message.colorify())
    }
}