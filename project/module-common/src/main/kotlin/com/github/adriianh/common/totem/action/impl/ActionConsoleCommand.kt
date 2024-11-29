package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class ActionConsoleCommand : Action<String>() {
    override val id: String = "CONSOLE"
    override val description: List<String> = listOf("Execute a command as console")

    private var command: String = "gamemode creative {player}"

    override fun getExampleValue(): String = "gamemode creative {player}"

    override fun getConvertedValue(value: Any?): String {
        return value as String
    }

    override fun setActionValue(value: Any) {
        command = getConvertedValue(value)
    }

    override fun getActionValue(): String {
        return command
    }

    override fun getMaterial(): XMaterial = XMaterial.PAPER

    override fun getItemName(): String = "&6Console".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Executes a command as console
            &7Example: &f${getExampleValue()}
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        Bukkit.dispatchCommand(
            Bukkit.getConsoleSender(), command.replace("{player}", player.name)
        )

        return true
    }
}