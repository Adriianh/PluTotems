package com.github.adriianh.common.totem.option.type.impl.trigger

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify

class OptionThrowable : OptionBase<Boolean>(OptionTypes.BASE) {
    override val id: String = "THROWABLE"
    override val description: List<String> = listOf("If the totem is throwable")
    override val optional: Boolean = true

    private var throwable: Boolean = true

    override fun getOptionPath(): String = "options.$identifier"

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Any) {
        throwable = getConvertedValue(value)
    }

    override fun getOptionValue(): Boolean {
        return throwable
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return when (value) {
            is Boolean -> value
            is String -> value.toBoolean()
            else -> throw IllegalArgumentException("Value is not a boolean")
        }
    }

    override fun getMaterial(): XMaterial = XMaterial.OAK_BUTTON

    override fun getItemName(): String = "&aThrowable".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's throwability
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            
        """.trimIndent().lines().colorify()
    }
}