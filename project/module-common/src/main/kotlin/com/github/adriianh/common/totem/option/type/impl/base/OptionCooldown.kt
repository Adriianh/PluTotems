package com.github.adriianh.common.totem.option.type.impl.base

import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify
import taboolib.library.xseries.XMaterial

class OptionCooldown : OptionBase<Int>() {
    override val id: String = "COOLDOWN"
    override val description: List<String> = listOf("Totem's cooldown")
    override val optional: Boolean = true

    private var cooldown: Int = 0

    override fun isTypeCompatible(value: Int): Boolean = true

    override fun getDefaultValue(): Int = 0

    override fun getExampleValue(): Int = 15

    override fun setOptionValue(value: Int) {
        cooldown = value
    }

    override fun getOptionValue(): Int {
        return cooldown
    }

    override fun getConvertedValue(value: Any?): Int {
        return value as Int
    }

    override fun getMaterial(): XMaterial = XMaterial.EXPERIENCE_BOTTLE

    override fun getItemName(): String = "&aCooldown".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's cooldown
            &7Example: &a10
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}