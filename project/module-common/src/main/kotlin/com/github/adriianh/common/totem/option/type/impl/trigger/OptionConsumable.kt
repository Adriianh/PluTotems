package com.github.adriianh.common.totem.option.type.impl.trigger

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify

class OptionConsumable : OptionBase<Boolean>() {
    override val id: String = "CONSUMABLE"
    override val description: List<String> = listOf("If the totem is consumable")
    override val optional: Boolean = true

    private var consumable: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Boolean) {
        consumable = value
    }

    override fun getOptionValue(): Boolean {
        return consumable
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }

    override fun getMaterial(): XMaterial = XMaterial.COOKED_BEEF

    override fun getItemName(): String = "&aConsumable".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's consumability
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            
        """.trimIndent().lines().colorify()
    }
}