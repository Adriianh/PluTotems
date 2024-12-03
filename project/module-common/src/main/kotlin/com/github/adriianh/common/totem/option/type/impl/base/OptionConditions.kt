package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify
import taboolib.library.configuration.ConfigurationSection

class OptionConditions : OptionBase<ConfigurationSection>(OptionTypes.BASE) {
    override val id: String = "CONDITIONS"
    override val description: List<String> = listOf("Totem's conditions")
    override val optional: Boolean = true

    private var conditions: Map<String, Any>? = null

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

        conditions = convertedValue.getValues(false).mapValues { it.value ?: "" as Any }
    }

    override fun getOptionValue(): Any {
        return conditions ?: getDefaultValue()
    }

    override fun getConvertedValue(value: Any?): ConfigurationSection {
        return value as ConfigurationSection
    }

    override fun getMaterial(): XMaterial = XMaterial.BOOK

    override fun getItemName(): String = "&aConditions".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's conditions

            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}