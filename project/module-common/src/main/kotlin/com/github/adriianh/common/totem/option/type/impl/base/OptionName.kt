package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify

class OptionName: OptionBase<String>() {
    override val id: String = "NAME"
    override val description: List<String> = listOf("Totem's name")
    override val optional: Boolean = false

    private var name: String = "Totem"

    override fun isTypeCompatible(value: String): Boolean = true

    override fun getDefaultValue(): String = "Totem"

    override fun getExampleValue(): String = "New Totem"

    override fun setOptionValue(value: String) {
        name = value
    }

    override fun getOptionValue(): String {
        return name
    }

    override fun getConvertedValue(value: Any?): String {
        return value as String
    }

    override fun getMaterial(): XMaterial = XMaterial.NAME_TAG

    override fun getItemName(): String = "&aName".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's name
            &7Example: &aTotem
            &7Default value: &a${getDefaultValue()}
            &7
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}