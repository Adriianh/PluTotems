package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import org.bukkit.entity.Player

object ActionFrozen : Action {
    override val identifier: String = "FROZEN"

    override fun execute(player: Player, value: String) {
        val time = value.toInt()
        player.freezeTicks = 140 + (time * 20)
    }
}