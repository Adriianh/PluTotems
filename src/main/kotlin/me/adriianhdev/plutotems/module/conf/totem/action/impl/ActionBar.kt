package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player
import taboolib.platform.util.sendActionBar

object ActionBar : Action {
    override val identifier: String = "ACTIONBAR"

    override fun execute(player: Player, value: String) {
        player.sendActionBar(value.colorify())
    }
}