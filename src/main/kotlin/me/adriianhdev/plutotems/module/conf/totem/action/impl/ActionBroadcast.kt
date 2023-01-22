package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionBroadcast : Action {
    override val identifier: String = "BROADCAST"

    override fun execute(player: Player, value: String) {
        player.server.broadcastMessage(
            value
                .replace("{player}", player.name)
                .colorify()
        )
    }
}