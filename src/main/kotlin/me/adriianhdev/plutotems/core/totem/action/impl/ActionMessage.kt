package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import me.adriianhdev.plutotems.util.color.colorify
import org.bukkit.entity.Player

object ActionMessage : Action {
    override val identifier: String = "MESSAGE"

    override fun execute(player: Player, value: String) {
        player.sendMessage(value.colorify())
    }
}