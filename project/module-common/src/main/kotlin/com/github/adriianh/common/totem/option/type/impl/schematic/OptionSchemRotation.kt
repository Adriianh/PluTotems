package com.github.adriianh.common.totem.option.type.impl.schematic

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.compat.schematic.SchemRotation
import com.github.adriianh.common.totem.option.type.OptionSchematic
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify
import taboolib.library.configuration.ConfigurationSection

class OptionSchemRotation  : OptionSchematic<SchemRotation>(OptionTypes.SCHEMATIC) {
    override val id: String = "ROTATION"
    override val description: List<String> = listOf("Totem's schematic rotation")
    override val optional: Boolean = true

    private var rotation: SchemRotation = SchemRotation()

    override fun getOptionPath(): String = "options.schematic.$identifier"

    override fun isTypeCompatible(value: SchemRotation): Boolean = true

    override fun getDefaultValue(): SchemRotation = SchemRotation()

    override fun getExampleValue(): SchemRotation = SchemRotation()

    override fun setOptionValue(value: Any) {
        rotation = getConvertedValue(value)
    }

    override fun getOptionValue(): SchemRotation {
        return rotation
    }

    override fun getConvertedValue(value: Any?): SchemRotation {
        val section = if (value is ConfigurationSection) {
            SchemRotation(
                x = value.getDouble("x"),
                y = value.getDouble("y"),
                z = value.getDouble("z"),
            )
        } else {
            SchemRotation()
        }

        return section
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