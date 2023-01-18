package me.adriianhdev.plutotems.module.conf.totem.action.impl

import org.bukkit.Bukkit
import org.bukkit.entity.Player

object ActionConsole {
    fun execute(player: Player, message: String) {
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(), message.replace("{player}", player.name)
        )
    }
}