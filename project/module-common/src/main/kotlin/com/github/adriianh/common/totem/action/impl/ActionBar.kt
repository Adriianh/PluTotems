package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player
import taboolib.platform.util.sendActionBar

class ActionBar() : Action<String>() {
    override val id: String = "ACTIONBAR"
    override val description: List<String> = listOf("Sends a message to the player's action bar.")

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

    override fun getItemName(): String = "&6Action Bar".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Sends a message to the player's action bar.
            &7Example: &fHello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        player.sendActionBar(message.colorify())
        return true
    }
}