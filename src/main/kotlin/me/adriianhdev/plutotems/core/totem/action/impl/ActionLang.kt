package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import org.bukkit.entity.Player
import taboolib.platform.util.sendLang

object ActionLang : Action {
    override val identifier = "LANG"

    override fun execute(player: Player, value: String) {
        player.sendLang(value)
    }
}