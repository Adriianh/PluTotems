package com.github.adriianh.common.totem.action.impl

import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial

class ActionTitle : Action<String>() {
    override val id: String = "TITLE"
    override val description: List<String> = listOf("Sends a message to the player")

    private var title: String = "Hello World!"

    override fun getExampleValue(): String = "ENTITY_VILLAGER_YES-1-1"

    override fun convertValue(value: Any?): String {
        return value as String
    }

    override fun setActionValue(value: String) {
        title = value
    }

    override fun getActionValue(): String {
        return title
    }

    override fun getMaterial(): XMaterial = XMaterial.PAPER

    override fun getItemName(): String = "&6Title".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Sends a title to the player.
            &7Example: &fHello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        val values = title.split("|", limit = 5)

        player.sendTitle(
            values[0].colorify(),
            values[1].colorify(),
            values[2].toIntOrNull() ?: 15,
            values[3].toIntOrNull() ?: 20,
            values[4].toIntOrNull() ?: 15
        )

        return true
    }
}