package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.KetherUtil
import org.bukkit.entity.Player

object ActionScript {
    fun execute(player: Player, script: String) {
        KetherUtil.eval(player, script)
    }
}