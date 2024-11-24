package com.github.adriianh.common.totem.option.type.impl.schematic

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionSchematic
import com.github.adriianh.common.util.colorify

class OptionSchemAir : OptionSchematic<Boolean>() {
    override val id: String = "SCHEMATICAIR"
    override val description: List<String> = listOf("Enable schematic air")
    override val optional: Boolean = true

    private var air: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Boolean) {
        air = value
    }

    override fun getOptionValue(): Boolean {
        return air
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }
    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aAir".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Enable schematic air
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}