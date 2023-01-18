package me.adriianhdev.plutotems.module.conf.totem.action

import me.adriianhdev.plutotems.module.conf.totem.action.impl.*
import org.bukkit.entity.Player
import taboolib.common.platform.function.warning
import java.util.*

object Action {
    fun execute(text: String, player: Player) {
        val actionType = text.split(":")[0].uppercase(Locale.getDefault())
        val actionValue = text.split(": ")[1]

        when (actionType) {
            "MESSAGE" -> ActionMessage.execute(player, actionValue)
            "JSON" -> ActionTellraw.execute(player, actionValue)
            "TITLE" -> ActionTitle.execute(player, actionValue)
            "SOUND" -> ActionSound.execute(player, actionValue)
            "COMMAND" -> ActionCommand.execute(player, actionValue)
            "CONSOLE" -> ActionConsole.execute(player, actionValue)
            "BROADCAST" -> ActionBroadcast.execute(player, actionValue)
            "ACTIONBAR" -> ActionBar.execute(player, actionValue)
            "BOSSBAR" -> ActionBossbar.execute(player, actionValue)
            "KETHER", "$", "SCRIPT" -> ActionScript.execute(player, actionValue)
            "EFFECT" -> ActionEffect.execute(player, actionValue)
            "PARTICLE" -> ActionParticle.execute(player, actionValue)
            "FIRE" -> ActionFire.execute(player, actionValue.toInt())
            "FROZEN" -> ActionFrozen.execute(player, actionValue.toInt())
            else -> { warning("Unknown action type: $actionType") }
        }
    }
}
