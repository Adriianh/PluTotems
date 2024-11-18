package com.github.adriianh.common.totem.action.impl

import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.KetherUtil.eval
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial

class ActionScript : Action<List<String>>() {
    override val id: String = "BOSSBAR"
    override val description: List<String> = listOf("Send a boss bar to the player")

    private var scripts: List<String> = listOf(
        ""
    )

    override fun getExampleValue(): String = "Hello World!"

    override fun convertValue(value: Any?): List<String> {
        return value as List<String>
    }

    override fun setActionValue(value: List<String>) {
        scripts = value
    }

    override fun getActionValue(): List<String> {
        return scripts
    }

    override fun getMaterial(): XMaterial = XMaterial.DRAGON_EGG

    override fun getItemName(): String = "&6Script".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Executes a list of scripts.
            &7Example: &fplayer allow flight to true
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        scripts.eval(player)

        return true
    }
}