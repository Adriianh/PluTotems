package com.github.adriianh.common.totem.condition.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.condition.Condition
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player
import taboolib.common.platform.function.adaptPlayer
import taboolib.common5.Coerce
import taboolib.module.kether.KetherShell
import taboolib.module.kether.ScriptOptions
import taboolib.module.kether.printKetherErrorMessage
import taboolib.platform.util.sendLang

class ConditionScript : Condition<List<String>>() {
    override val id: String = "SCRIPT"
    override val description: List<String> = listOf("Script required to use the totem")

    private var script = listOf("check player level < 50")

    override fun getConvertedValue(value: Any?): List<String> {
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

    override fun setConditionValue(value: Any) {
        script = getConvertedValue(value)
    }

    override fun getConditionValue(): List<String> {
        return script
    }

    override fun checkResult(player: Player): Boolean {
        if (script.isNotEmpty()) {
            val result = KetherShell.eval(script, ScriptOptions(sender = adaptPlayer(player)))
                .thenApply {
                    Coerce.toBoolean(it)
                }.exceptionally {
                    it.printKetherErrorMessage()
                    false
                }.get()

            if (!result) {
                player.sendLang("Totem-Script-Failed")
                return false
            }
        }
        return true
    }

    override fun getExampleValue(): List<String> {
        return listOf("check player level < 50")
    }

    override fun getMaterial(): XMaterial = XMaterial.PAPER

    override fun getItemName(): String = "&eScript".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Script required to use the totem
            &7Example: &etotem.use
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }
}