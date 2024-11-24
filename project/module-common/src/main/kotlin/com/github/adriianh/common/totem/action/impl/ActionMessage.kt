package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player

class ActionMessage : Action<String>() {
    override val id: String = "MESSAGE"
    override val description: List<String> = listOf("Sends a message to the player")

    private var message: String = "Hello world!"

    override fun getExampleValue(): String = "Hello world!"

    override fun convertValue(value: Any?): String {
        return value as String
    }

    override fun setActionValue(value: String) {
        message = value
    }

    override fun getActionValue(): String {
        return message
    }

    override fun getMaterial(): XMaterial = XMaterial.PAPER

    override fun getItemName(): String = "&6Message".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Sends a message to the player.
            &7Example: &fHello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        player.sendMessage(message.colorify())
        return true
    }
}