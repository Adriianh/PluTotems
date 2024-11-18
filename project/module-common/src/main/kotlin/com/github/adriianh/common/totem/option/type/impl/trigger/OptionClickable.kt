package com.github.adriianh.common.totem.option.type.impl.trigger

import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify
import taboolib.library.xseries.XMaterial

class OptionClickable: OptionBase<Boolean>() {
    override val id: String = "CLICKABLE"
    override val description: List<String> = listOf("If the totem is clickable")
    override val optional: Boolean = true

    private var clickable: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Boolean) {
        clickable = value
    }

    override fun getOptionValue(): Boolean {
        return clickable
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }

    override fun getMaterial(): XMaterial = XMaterial.OAK_BUTTON

    override fun getItemName(): String = "&aClickable".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's clickability
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            
        """.trimIndent().lines().colorify()
    }
}