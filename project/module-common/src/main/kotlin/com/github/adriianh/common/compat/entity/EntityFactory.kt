package com.github.adriianh.common.compat.entity

import com.github.adriianh.common.compat.OptionParameters
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity

object EntityFactory {
    private fun setEntityEquipment(entity: LivingEntity, equipment: EntityEquipment) {
        val entityEquipment = entity.equipment ?: return

        entityEquipment.helmet = equipment.helmet
        entityEquipment.chestplate = equipment.chestplate
        entityEquipment.leggings = equipment.leggings
        entityEquipment.boots = equipment.boots
        entityEquipment.setItemInMainHand(equipment.hand)
        entityEquipment.setItemInOffHand(equipment.offhand)
    }

    private fun setEntityRotation(entity: LivingEntity, rotation: EntityRotation) {
        entity.setRotation(rotation.yaw, rotation.pitch)
        entity.location.direction = rotation.direction
    }

    fun spawnMobEntity(type: EntityType, location: Location, options: OptionParameters): Entity? {
        val equipment = options.getParameters("entityequipment") as? EntityEquipment
        val rotation = options.getParameters("entitydirection") as? EntityRotation

        return EntityBuilder.asType(type, location)
            .name(options.getParameters("entityname") as? String)
            .hasAI(options.getParameters("entityai") as? Boolean ?: true)
            .hasGravity(options.getParameters("entitygravity") as? Boolean ?: true)
            .isNameVisible(options.getParameters("entitynamevisible") as? Boolean ?: true)
            .isInvulnerable(options.getParameters("entityinvulnerable") as? Boolean ?: false)
            .isGlowing(options.getParameters("entityglowing") as? Boolean ?: false)
            .isCollidable(options.getParameters("entitycollidable") as? Boolean ?: true)
            .isInvisible(options.getParameters("entityinvisible") as? Boolean ?: false)
            .addPassenger(options.getParameters("entitypassenger") as? Entity)
            .add { entity -> setEntityEquipment(entity!!, equipment ?: EntityEquipment()) }
            .add { entity -> setEntityRotation(entity!!, rotation ?: EntityRotation()) }
            .build()
    }

    fun spawnArmorStandEntity(type: EntityType, location: Location, options: OptionParameters): ArmorStand? {
        val equipment = options.getParameters("entityequipment") as? EntityEquipment
        val rotation = options.getParameters("entitydirection") as? EntityRotation
        val pose = options.getParameters("entitypose") as? EntityPose

        return EntityBuilder.asType(type, location)
            .name(options.getParameters("entityname") as? String)
            .hasAI(options.getParameters("entityai") as? Boolean ?: true)
            .hasGravity(options.getParameters("entitygravity") as? Boolean ?: true)
            .isNameVisible(options.getParameters("entitynamevisible") as? Boolean ?: true)
            .isInvulnerable(options.getParameters("entityinvulnerable") as? Boolean ?: false)
            .isGlowing(options.getParameters("entityglowing") as? Boolean ?: false)
            .isCollidable(options.getParameters("entitycollidable") as? Boolean ?: true)
            .isInvisible(options.getParameters("entityinvisible") as? Boolean ?: false)
            .addPassenger(options.getParameters("entitypassenger") as? Entity)
            .add { entity -> setEntityEquipment(entity!!, equipment ?: EntityEquipment()) }
            .add { entity -> setEntityRotation(entity!!, rotation ?: EntityRotation()) }
            .add(ArmorStand::class.java) { entity: ArmorStand ->
                entity.setArms(options.getParameters("entityarms") as? Boolean ?: false)
                entity.setBasePlate(options.getParameters("entitybaseplate") as? Boolean ?: false)
                entity.isMarker = options.getParameters("entitymarker") as? Boolean ?: false
                entity.isSmall = options.getParameters("entitysmall") as? Boolean ?: false
                entity.isVisible = options.getParameters("entityvisible") as? Boolean ?: true
            }
            .add(ArmorStand::class.java) { entity: ArmorStand ->
                entity.headPose = pose!!.head.asEulerAngle()
                entity.bodyPose = pose.body.asEulerAngle()
                entity.leftArmPose = pose.leftArm.asEulerAngle()
                entity.rightArmPose = pose.rightArm.asEulerAngle()
                entity.leftLegPose = pose.leftLeg.asEulerAngle()
                entity.rightLegPose = pose.rightLeg.asEulerAngle()
            }
            .build()
    }
}