package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import me.adriianhdev.plutotems.plugin.PluTotemsLoader.adventure
import me.adriianhdev.plutotems.util.StringUtil.toMiniMessage
import org.bukkit.entity.Player

object ActionMiniMessage : Action {
    override val identifier = "MINIMESSAGE"

    override fun execute(player: Player, value: String) {
        adventure()
            .player(player)
            .sendMessage(value.toMiniMessage())
    }
}