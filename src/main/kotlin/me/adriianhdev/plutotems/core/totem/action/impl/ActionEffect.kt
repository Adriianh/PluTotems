package me.adriianhdev.plutotems.core.totem.action.impl

import me.adriianhdev.plutotems.core.totem.action.Action
import org.bukkit.EntityEffect
import org.bukkit.entity.Player

object ActionEffect : Action {
    override val identifier: String = "EFFECT"

    override fun execute(player: Player, value: String) {
        player.playEffect(EntityEffect.valueOf(value))
    }
}