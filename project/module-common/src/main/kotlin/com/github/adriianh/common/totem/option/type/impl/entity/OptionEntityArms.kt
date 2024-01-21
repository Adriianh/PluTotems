package com.github.adriianh.common.totem.option.type.impl.entity

import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.util.colorify
import taboolib.library.xseries.XMaterial

class OptionEntityArms : OptionEntity<Boolean>() {
    override val id: String = "ENTITYARMS"
    override val description: List<String> = listOf("Enable entity arms")
    override val optional: Boolean = true

    private var arms: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Boolean) {
        arms = value
    }

    override fun getOptionValue(): Boolean {
        return arms
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }
    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aArms".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Enable entity arms
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}