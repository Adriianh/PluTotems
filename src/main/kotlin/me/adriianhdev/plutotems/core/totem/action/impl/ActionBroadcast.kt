package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import me.adriianhdev.plutotems.util.color.colorify
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