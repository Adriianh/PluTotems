package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.Option
import com.github.adriianh.common.util.colorify
import taboolib.library.configuration.ConfigurationSection

class OptionItem : Option<ConfigurationSection>() {
    override val id: String = "ITEM"
    override val description: List<String> = listOf("Totem's item")
    override val optional: Boolean = false

    private var item: Map<String, Any>? = null

    override fun isTypeCompatible(value: ConfigurationSection): Boolean = true

    override fun getDefaultValue(): Map<String, Any> {
        return mapOf()
    }

    override fun getExampleValue(): Map<String, Any> {
        return mapOf()
    }

    override fun setOptionValue(value: Any) {
        val convertedValue = getConvertedValue(value)

        item = convertedValue.getValues(false).mapValues { it.value ?: "" as Any }
    }

    override fun getOptionValue(): Any {
        return item ?: getDefaultValue()
    }

    override fun getConvertedValue(value: Any?): ConfigurationSection {
        return value as ConfigurationSection
    }

    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aItem".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's item
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}