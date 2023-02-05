package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player
import taboolib.platform.util.sendLang

object ActionLang : Action {
    override val identifier = "LANG"

    override fun execute(player: Player, value: String) {
        player.sendLang(value)
    }
}