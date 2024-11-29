package com.github.adriianh.common.totem.option.type.impl.schematic

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionSchematic
import com.github.adriianh.common.util.colorify

class OptionSchemEntities : OptionSchematic<Boolean>() {
    override val id: String = "SCHEMATICENTITIES"
    override val description: List<String> = listOf("Enable schematic entities")
    override val optional: Boolean = true

    private var entities: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Any) {
        entities = getConvertedValue(value)
    }

    override fun getOptionValue(): Boolean {
        return entities
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }
    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aEntities".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Enable schematic entities
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}