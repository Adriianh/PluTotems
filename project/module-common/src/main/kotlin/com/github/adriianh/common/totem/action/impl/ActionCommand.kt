package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player

class ActionCommand : Action<String>() {
    override val id: String = "COMMAND"
    override val description: List<String> = listOf("Executes a command as the player.")

    private var command: String = "say Hello World!"

    override fun getExampleValue(): String = "say Hello world!"

    override fun convertValue(value: Any?): String {
        return value as String
    }

    override fun setActionValue(value: String) {
        command = value
    }

    override fun getActionValue(): String {
        return command
    }

    override fun getMaterial(): XMaterial = XMaterial.COMMAND_BLOCK

    override fun getItemName(): String = "&6Command".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Executes a command as the player.
            &7Example: &f/say Hello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        player.performCommand(command)
        return true
    }
}