package me.adriianhdev.plutotems.core.totem.action.impl

import ink.ptms.um.Mythic
import me.adriianhdev.plutotems.core.totem.action.Action
import org.bukkit.entity.Player

object ActionSkill : Action {
    override val identifier: String = "SKILL"

    override fun execute(player: Player, value: String) {
        Mythic.API.castSkill(player, value)
    }
}