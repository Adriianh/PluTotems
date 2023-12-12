package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object ActionConsole : Action {
    override val identifier: String = "CONSOLE"

    override fun execute(player: Player, value: String) {
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(), value.replace("{player}", player.name)
        )
    }
}