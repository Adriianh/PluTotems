package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import org.bukkit.entity.Player

object ActionTellraw : Action {
    override val identifier: String = "TELLRAW"

    override fun execute(player: Player, value: String) {
        player.sendRawMessage(value)
    }
}