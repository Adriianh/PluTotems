package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionFire: Action {
    override val identifier: String = "FIRE"

    override fun execute(player: Player, value: String) {
        val time = value.toInt()
        player.fireTicks = 140 + (time * 20)
    }
}