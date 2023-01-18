package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import org.bukkit.entity.Player
import taboolib.platform.util.sendActionBar

object ActionBar {
    fun execute(player: Player, message: String) {
        player.sendActionBar(message.colorify())
    }
}