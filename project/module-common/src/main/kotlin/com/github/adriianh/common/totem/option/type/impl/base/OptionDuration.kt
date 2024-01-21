package com.github.adriianh.common.totem.option.type.impl.base

import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify
import taboolib.library.xseries.XMaterial

class OptionDuration : OptionBase<Int>() {
    override val id: String = "DURATION"
    override val description: List<String> = listOf("Totem's duration")
    override val optional: Boolean = true

    private var duration: Int = 0

    override fun isTypeCompatible(value: Int): Boolean = true

    override fun getDefaultValue(): Int = 10

    override fun getExampleValue(): Int = 30

    override fun setOptionValue(value: Int) {
        duration = value
    }

    override fun getOptionValue(): Int {
        return duration
    }

    override fun getConvertedValue(value: Any?): Int {
        return value as Int
    }

    override fun getMaterial(): XMaterial = XMaterial.TOTEM_OF_UNDYING

    override fun getItemName(): String = "&aDuration".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's duration
            &7Example: &a10
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}