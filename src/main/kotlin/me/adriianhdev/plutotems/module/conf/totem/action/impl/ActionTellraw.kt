package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionTellraw : Action {
    override val identifier: String = "TELLRAW"

    override fun execute(player: Player, value: String) {
        player.sendRawMessage(value)
    }
}