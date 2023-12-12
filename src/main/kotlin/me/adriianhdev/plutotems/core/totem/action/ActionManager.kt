package me.adriianhdev.plutotems.core.totem.action

import me.adriianhdev.plutotems.core.totem.action.impl.*
import org.bukkit.entity.Player
import taboolib.common.platform.function.warning
import java.util.*

object ActionManager {
    private val actions = mutableMapOf<String, Action>()

    private fun registerAction(vararg actions: Action) {
        actions.forEach { action ->
            this.actions[action.identifier] = action
        }
    }

    private fun getAction(identifier: String): Action? {
        return actions[identifier]
    }

    init {
        registerAction(
            ActionBar, ActionBossbar, ActionBroadcast, ActionCommand, ActionConsole,
            ActionEffect, ActionFire, ActionFrozen, ActionMessage, ActionParticle,
            ActionScript, ActionSound, ActionTellraw, ActionTitle, ActionLang,
            ActionMiniMessage, ActionSkill
        )
    }

    fun execute(text: String, player: Player) {
        val actionType = text.split(":")[0].uppercase(Locale.getDefault())
        val actionValue = text.split(": ")[1]

        val action = getAction(actionType)
        if (action == null) {
            warning("Action $actionType not found!")
            return
        }

        action.execute(player, actionValue)
    }
}
