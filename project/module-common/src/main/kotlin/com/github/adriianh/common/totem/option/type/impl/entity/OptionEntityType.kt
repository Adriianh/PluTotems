package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.EntityType

class OptionEntityType : OptionEntity<EntityType>(OptionTypes.ENTITY) {
    override val id: String = "TYPE"
    override val description: List<String> = listOf("Totem's entity type")
    override val optional: Boolean = true

    private var entityType: EntityType = EntityType.ZOMBIE

    override fun getOptionPath(): String = "options.entity.$identifier"

    override fun isTypeCompatible(value: EntityType): Boolean = true

    override fun getDefaultValue(): EntityType = EntityType.ZOMBIE

    override fun getExampleValue(): EntityType = EntityType.ZOMBIE

    override fun setOptionValue(value: Any) {
        entityType = getConvertedValue(value)
    }

    override fun getOptionValue(): EntityType {
        return entityType
    }

    override fun getConvertedValue(value: Any?): EntityType {
        return EntityType.valueOf(value as String)
    }

    override fun getMaterial(): XMaterial = XMaterial.ZOMBIE_HEAD

    override fun getItemName(): String = "&aEntity Type".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's entity
            &7Example: &aSKELETON
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}