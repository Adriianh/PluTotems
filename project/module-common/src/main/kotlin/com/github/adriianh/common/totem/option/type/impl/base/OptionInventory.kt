package com.github.adriianh.common.totem.option.type.impl.base

import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify
import taboolib.library.xseries.XMaterial

class OptionInventory : OptionBase<Boolean>() {
    override val id: String = "INVENTORY"
    override val description: List<String> = listOf("If the totem should be executed from the inventory")
    override val optional: Boolean = true

    private var enabled: Boolean = false

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = false

    override fun getExampleValue(): Boolean = true

    override fun setOptionValue(value: Boolean) {
        enabled = value
    }

    override fun getOptionValue(): Boolean {
        return enabled
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }

    override fun getMaterial(): XMaterial = XMaterial.TOTEM_OF_UNDYING

    override fun getItemName(): String = "&aInventory".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Enable or disable inventory totem
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}