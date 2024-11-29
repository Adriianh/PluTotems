package com.github.adriianh.common.totem.option.type.impl.schematic

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionSchematic
import com.github.adriianh.common.util.colorify

class OptionSchem : OptionSchematic<String>() {
    override val id: String = "SCHEMATICNAME"
    override val description: List<String> = listOf("Totem's schematic")
    override val optional: Boolean = true

    private var schematic: String = "House"

    override fun isTypeCompatible(value: String): Boolean = true

    override fun getDefaultValue(): String = "House"

    override fun getExampleValue(): String = "Altar"

    override fun setOptionValue(value: Any) {
        schematic = getConvertedValue(value)
    }

    override fun getOptionValue(): String {
        return schematic
    }

    override fun getConvertedValue(value: Any?): String {
        return value as String
    }

    override fun getMaterial(): XMaterial = XMaterial.NAME_TAG

    override fun getItemName(): String = "&aSchematic".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's schematic
            &7Example: &aDojo
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}