package com.github.adriianh.common.totem.option.type.impl.entity

import com.github.adriianh.common.compat.entity.EntityEquipment
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.util.ConfigUtil.getItemStack
import com.github.adriianh.common.util.colorify
import taboolib.library.configuration.ConfigurationSection
import taboolib.library.xseries.XMaterial

class OptionEntityEquipment : OptionEntity<EntityEquipment>() {
    override val id: String = "ENTITYEQUIPMENT"
    override val description: List<String> = listOf("Entity's equipment")
    override val optional: Boolean = true

    private var equipment: EntityEquipment = EntityEquipment()

    override fun isTypeCompatible(value: EntityEquipment): Boolean = true

    override fun getDefaultValue(): EntityEquipment = EntityEquipment()

    override fun getExampleValue(): EntityEquipment = EntityEquipment(
        helmet = XMaterial.DIAMOND_HELMET.parseItem(),
        leggings = XMaterial.DIAMOND_LEGGINGS.parseItem(),
        offhand = XMaterial.SHIELD.parseItem()
    )

    override fun setOptionValue(value: EntityEquipment) {
        equipment = value
    }

    override fun getOptionValue(): EntityEquipment {
        return equipment
    }

    override fun getConvertedValue(value: Any?): EntityEquipment {
        val section = if (value is ConfigurationSection) {
            EntityEquipment(
                helmet = getItemStack(value, "helmet"),
                chestplate = getItemStack(value, "chestplate"),
                leggings = getItemStack(value, "leggings"),
                boots = getItemStack(value, "boots")
            )
        } else {
            EntityEquipment()
        }

        return section
    }

    override fun getMaterial(): XMaterial = XMaterial.IRON_HELMET

    override fun getItemName(): String = "&aEquipment".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit entity's equipment
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}