package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.plugin.PluTotemsLoader.adventure
import me.adriianhdev.plutotems.common.util.StringUtil.toMiniMessage
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionMiniMessage : Action {
    override val identifier = "MINIMESSAGE"

    override fun execute(player: Player, value: String) {
        adventure()
            .player(player)
            .sendMessage(value.toMiniMessage())
    }
}