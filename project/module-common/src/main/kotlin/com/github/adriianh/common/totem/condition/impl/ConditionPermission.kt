package com.github.adriianh.common.totem.condition.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.condition.Condition
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player
import taboolib.platform.util.sendLang

class ConditionPermission : Condition<String>() {
    override val id: String = "PERMISSION"
    override val description: List<String> = listOf("Permission required to use the totem")

    private var permission: String = "plutotems.totem.usage"

    override fun getConvertedValue(value: Any?): String {
        return value as? String ?: throw IllegalArgumentException("Value is not a string")
    }

    override fun setConditionValue(value: Any) {
        permission = getConvertedValue(value)
    }

    override fun getConditionValue(): String {
        return permission
    }

    override fun checkResult(player: Player): Boolean {
        if (permission.isNotEmpty() && !player.hasPermission(permission) && !player.isOp) {
            player.sendLang("Totem-No-Permission")
            return false
        }
        return true
    }

    override fun getExampleValue(): String {
        return "totem.use"
    }

    override fun getMaterial(): XMaterial = XMaterial.PAPER

    override fun getItemName(): String = "&ePermission".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Permission required to use the totem
            &7Example: &etotem.use
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }
}