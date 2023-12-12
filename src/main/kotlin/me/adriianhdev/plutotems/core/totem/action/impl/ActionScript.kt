package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import me.adriianhdev.plutotems.util.KetherUtil
import org.bukkit.entity.Player

object ActionScript : Action {
    override val identifier: String = "SCRIPT"

    override fun execute(player: Player, value: String) {
        KetherUtil.eval(player, value)
    }
}