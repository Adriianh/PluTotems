package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.KetherUtil.eval
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player

class ActionScript : Action<List<String>>() {
    override val id: String = "SCRIPT"
    override val description: List<String> = listOf("Executes a list of scripts")

    private var scripts: List<String> = listOf(
        "player allow flight to true",
        "player swimming to true"
    )

    override fun getExampleValue(): List<String> {
        return scripts
    }

    override fun convertValue(value: Any?): List<String> {
        return when (value) {
            is List<*> -> {
                value.filterIsInstance<String>()
            }
            is String -> {
                listOf(value)
            }
            else -> {
                emptyList()
            }
        }
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