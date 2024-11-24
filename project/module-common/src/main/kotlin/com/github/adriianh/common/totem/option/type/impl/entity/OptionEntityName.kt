package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.util.colorify

class OptionEntityName : OptionEntity<String>() {
    override val id: String = "ENTITYNAME"
    override val description: List<String> = listOf("Totem's entity type")
    override val optional: Boolean = true

    private var entityType: String = "Totem's entity"

    override fun isTypeCompatible(value: String): Boolean = true

    override fun getDefaultValue(): String = "Totem's entity"

    override fun getExampleValue(): String = "Angel"

    override fun setOptionValue(value: String) {
        entityType = value
    }

    override fun getOptionValue(): String {
        return entityType
    }

    override fun getConvertedValue(value: Any?): String {
        return value as String
    }

    override fun getMaterial(): XMaterial = XMaterial.NAME_TAG

    override fun getItemName(): String = "&aEntity Name".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's entity name
            &7Example: &aAngel
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}