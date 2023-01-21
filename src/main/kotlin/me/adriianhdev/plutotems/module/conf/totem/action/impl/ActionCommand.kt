package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionCommand: Action {
    override val identifier: String = "COMMAND"

    override fun execute(player: Player, value: String) {
        player.performCommand(value.replace("{player}", player.name))
    }
}