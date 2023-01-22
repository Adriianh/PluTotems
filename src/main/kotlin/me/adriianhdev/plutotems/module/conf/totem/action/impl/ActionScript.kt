package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.KetherUtil
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player

object ActionScript : Action {
    override val identifier: String = "SCRIPT"

    override fun execute(player: Player, value: String) {
        KetherUtil.eval(player, value)
    }
}