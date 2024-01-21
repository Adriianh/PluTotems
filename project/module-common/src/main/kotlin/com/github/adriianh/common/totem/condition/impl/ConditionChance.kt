package com.github.adriianh.common.totem.condition.impl

import com.github.adriianh.common.totem.condition.Condition
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.sendLang

class ConditionChance : Condition<Int>() {
    override val id = "CHANCE"
    override val description = listOf("Chance of the totem to be activated")

    private var value: Int = 100

    override fun convertValue(value: Any?): Int {
        return value as? Int ?: throw IllegalArgumentException("Value is not an integer")
    }

    override fun setConditionValue(value: Int) {
        this.value = value
    }

    override fun getConditionValue(): Int {
        return value
    }

    override fun checkResult(player: Player): Boolean {
        if (value != 100 && (0..100).random() > value) {
            player.sendLang("Totem-Chance-Failed")
            return false
        }
        return true
    }

    override fun getExampleValue(): Int {
        return 50
    }

    override fun getMaterial() = XMaterial.GOLD_NUGGET

    override fun getItemName() = "&eChance".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Chance of the totem to be activated
            &7Example: &e50
            &7
            &7100 &7means that the totem will always be activated
            &70 &7means that the totem will never be activated
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }
}