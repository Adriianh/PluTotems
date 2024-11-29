package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.util.colorify

class OptionEntityCustom : OptionEntity<String>() {
    override val id: String = "ENTITYMODEL"
    override val description: List<String> = listOf("Totem's custom entity (model)")
    override val optional: Boolean = true

    private var model: String = "Squirrel"

    override fun isTypeCompatible(value: String): Boolean = true

    override fun getDefaultValue(): String = "Angel"

    override fun getExampleValue(): String = "Golem"

    override fun setOptionValue(value: Any) {
        model = getConvertedValue(value)
    }

    override fun getOptionValue(): String {
        return model
    }

    override fun getConvertedValue(value: Any?): String {
        return value as String
    }

    override fun getMaterial(): XMaterial = XMaterial.NAME_TAG

    override fun getItemName(): String = "&aEntity Model".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's entity model
            &7Example: &aAngel
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}