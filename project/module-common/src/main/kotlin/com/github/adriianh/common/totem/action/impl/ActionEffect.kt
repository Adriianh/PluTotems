package com.github.adriianh.common.totem.action.impl

import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.EntityEffect
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial

class ActionEffect : Action<String>() {
    override val id: String = "ANIMATION"
    override val description: List<String> = listOf("Plays an entity effect")

    private var animation: String = "TELEPORT_ENDER"

    override fun getExampleValue(): String = "TELEPORT_ENDER"

    override fun convertValue(value: Any?): String {
        return value as String
    }

    override fun setActionValue(value: String) {
        animation = value
    }

    override fun getActionValue(): String {
        return animation
    }

    override fun getMaterial(): XMaterial = XMaterial.ENDER_PEARL

    override fun getItemName(): String = "&6Effect".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Plays an entity effect
            &7Example: &fHello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        player.playEffect(EntityEffect.valueOf(animation))
        return true
    }
}