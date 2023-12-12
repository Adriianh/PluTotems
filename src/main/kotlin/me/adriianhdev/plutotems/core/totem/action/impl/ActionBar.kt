package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import me.adriianhdev.plutotems.util.color.colorify
import org.bukkit.entity.Player
import taboolib.platform.util.sendActionBar

object ActionBar : Action {
    override val identifier: String = "ACTIONBAR"

    override fun execute(player: Player, value: String) {
        player.sendActionBar(value.colorify())
    }
}