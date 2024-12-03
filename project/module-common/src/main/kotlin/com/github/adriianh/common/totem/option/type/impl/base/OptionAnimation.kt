package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify

class OptionAnimation : OptionBase<Boolean>(OptionTypes.BASE) {
    override val id: String = "ANIMATION"
    override val description: List<String> = listOf("Totem's animation")
    override val optional: Boolean = true

    private var enabled: Boolean = true

    override fun getOptionPath(): String = "options.$identifier"

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Any) {
        enabled = getConvertedValue(value)
    }

    override fun getOptionValue(): Boolean {
        return enabled
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return when (value) {
            is Boolean -> value
            is String -> value.toBoolean()
            else -> throw IllegalArgumentException("Value is not a boolean")
        }
    }

    override fun getMaterial(): XMaterial = XMaterial.TOTEM_OF_UNDYING

    override fun getItemName(): String = "&aAnimation".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Enable or disable totem's animation
            &7Example: &afalse
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}