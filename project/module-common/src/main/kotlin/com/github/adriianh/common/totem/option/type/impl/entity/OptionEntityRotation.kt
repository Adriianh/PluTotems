package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.compat.entity.EntityRotation
import com.github.adriianh.common.compat.entity.getVector
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify
import taboolib.library.configuration.ConfigurationSection

class OptionEntityRotation : OptionEntity<EntityRotation>(OptionTypes.ENTITY) {
    override val id: String = "ROTATION"
    override val description: List<String> = listOf("Entity's equipment")
    override val optional: Boolean = true

    private var pose: EntityRotation = EntityRotation()

    override fun getOptionPath(): String = "options.entity.$identifier"

    override fun isTypeCompatible(value: EntityRotation): Boolean = true

    override fun getDefaultValue(): EntityRotation = EntityRotation()

    override fun getExampleValue(): EntityRotation = EntityRotation()

    override fun setOptionValue(value: Any) {
        pose = getConvertedValue(value)
    }

    override fun getOptionValue(): EntityRotation {
        return pose
    }

    override fun getConvertedValue(value: Any?): EntityRotation {
        val section = if (value is ConfigurationSection) {
            EntityRotation(
                yaw = value.getDouble("yaw").toFloat(),
                pitch = value.getDouble("pitch").toFloat(),
                direction = getVector(value, "direction")
            )
        } else {
            EntityRotation()
        }

        return section
    }

    override fun getMaterial(): XMaterial = XMaterial.IRON_HELMET

    override fun getItemName(): String = "&aPose".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit entity's pose
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}