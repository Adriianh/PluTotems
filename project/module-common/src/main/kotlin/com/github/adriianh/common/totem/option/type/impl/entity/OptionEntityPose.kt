package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.compat.entity.EntityPose
import com.github.adriianh.common.compat.entity.getVector
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify
import taboolib.library.configuration.ConfigurationSection

class OptionEntityPose : OptionEntity<EntityPose>(OptionTypes.ENTITY) {
    override val id: String = "EQUIPMENT"
    override val description: List<String> = listOf("Entity's equipment")
    override val optional: Boolean = true

    private var pose: EntityPose = EntityPose()

    override fun getOptionPath(): String = "options.entity.$identifier"

    override fun isTypeCompatible(value: EntityPose): Boolean = true

    override fun getDefaultValue(): EntityPose = EntityPose()

    override fun getExampleValue(): EntityPose = EntityPose()

    override fun setOptionValue(value: Any) {
        pose = getConvertedValue(value)
    }

    override fun getOptionValue(): EntityPose {
        return pose
    }

    override fun getConvertedValue(value: Any?): EntityPose {
        val section = if (value is ConfigurationSection) {
            EntityPose(
                head = getVector(value, "head"),
                body = getVector(value, "body"),
                leftArm = getVector(value, "leftArm"),
                rightArm = getVector(value, "rightArm"),
                leftLeg = getVector(value, "leftLeg"),
                rightLeg = getVector(value, "rightLeg")
            )
        } else {
            EntityPose()
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