package com.github.adriianh.common.totem.action

import com.github.adriianh.common.totem.action.impl.*
import java.util.*

object ActionRegistry {
    private val actions = mutableMapOf<String, Action<*>>()

    init {
        registerActions(
            ActionBar(), ActionBossBar(), ActionBroadcast(), ActionCommand(),
            ActionConsoleCommand(), ActionEffect(), ActionFire(), ActionFrozen(),
            ActionMessage(), ActionScript(), ActionSound(), ActionTitle()
        )
    }

    private fun registerAction(action: Action<*>) {
        actions[action.identifier] = action
    }

    private fun registerActions(vararg actions: Action<*>) {
        actions.forEach { registerAction(it) }
    }

    fun getAction(identifier: String): Action<*>? {
        val action = actions[identifier.uppercase(Locale.getDefault())]?.clone()

        return action
    }

    fun getActions(): List<Action<*>> {
        return actions.values.toList()
    }
}