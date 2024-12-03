package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify
import taboolib.library.configuration.ConfigurationSection

class OptionEffects : OptionBase<ConfigurationSection>(OptionTypes.BASE) {
    override val id: String = "EFFECTS"
    override val description: List<String> = listOf("Totem's effects")
    override val optional: Boolean = true

    private var effects: Map<String, Any>? = null

    override fun getOptionPath(): String = identifier

    override fun isTypeCompatible(value: ConfigurationSection): Boolean = true

    override fun getDefaultValue(): Map<String, Any> {
        return mapOf()
    }

    override fun getExampleValue(): Map<String, Any> {
        return mapOf()
    }

    override fun setOptionValue(value: Any) {
        val convertedValue = getConvertedValue(value)

        effects = convertedValue.getValues(false).mapValues { it.value ?: "" as Any }
    }

    override fun getOptionValue(): Any {
        return effects ?: getDefaultValue()
    }

    override fun getConvertedValue(value: Any?): ConfigurationSection {
        return value as ConfigurationSection
    }

    override fun getMaterial(): XMaterial = XMaterial.POTION

    override fun getItemName(): String = "&aEffects".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's effects

            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}