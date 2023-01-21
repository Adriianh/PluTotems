package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionMessage: Action {
    override val identifier: String = "MESSAGE"

    override fun execute(player: Player, value: String) {
        player.sendMessage(value.colorify())
    }
}