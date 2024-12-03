package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.TotemType
import com.github.adriianh.common.totem.getTypeByName
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify

class OptionType : OptionBase<TotemType>(OptionTypes.BASE) {
    override val id: String = "TYPE"
    override val description: List<String> = listOf("Totem's actions")
    override val optional: Boolean = true

    private var totemType: TotemType = TotemType.ITEM

    override fun getOptionPath(): String = identifier

    override fun isTypeCompatible(value: TotemType): Boolean = true

    override fun getDefaultValue(): TotemType = TotemType.ITEM

    override fun getExampleValue(): TotemType = TotemType.ENTITY

    override fun setOptionValue(value: Any) {
        totemType = getConvertedValue(value)
    }

    override fun getOptionValue(): TotemType {
        return totemType
    }

    override fun getConvertedValue(value: Any?): TotemType {
        return when (value) {
            is TotemType -> value
            is String -> getTypeByName(value)
            else -> throw IllegalArgumentException("Unsupported value type: ${value?.javaClass?.name}")
        }
    }

    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aType".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's type
            &7Example: &aSTRUCTURE
            &7Default value: &a${getDefaultValue()}

            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}