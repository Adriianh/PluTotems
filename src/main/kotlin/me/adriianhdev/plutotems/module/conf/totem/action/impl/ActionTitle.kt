package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionTitle : Action {
    override val identifier: String = "TITLE"

    override fun execute(player: Player, value: String) {
        val split = value.split(" | ", limit = 5)

        val title = split[0].colorify()
        val subtitle = split.getOrNull(1)?.colorify()
        val fadeIn = split.getOrNull(2)?.toIntOrNull() ?: 15
        val stay = split.getOrNull(3)?.toIntOrNull() ?: 20
        val fadeOut = split.getOrNull(4)?.toIntOrNull() ?: 15

        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut)
    }
}