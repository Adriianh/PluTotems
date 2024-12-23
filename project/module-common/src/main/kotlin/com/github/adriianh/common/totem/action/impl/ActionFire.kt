package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player

class ActionFire : Action<Int>() {
    override val id: String = "FIRE"
    override val description: List<String> = listOf("Sets the player on fire")

    private var duration: Int = 15

    override fun getExampleValue(): Int = 10

    override fun getConvertedValue(value: Any?): Int {
        return value as Int
    }

    override fun setActionValue(value: Any) {
        duration = getConvertedValue(value)
    }

    override fun getActionValue(): Int {
        return duration
    }

    override fun getMaterial(): XMaterial = XMaterial.FIRE_CHARGE

    override fun getItemName(): String = "&6Fire".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Sets the player on fire
            &7Example: &fHello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        player.fireTicks = 140 + (duration * 20)
        return true
    }
}