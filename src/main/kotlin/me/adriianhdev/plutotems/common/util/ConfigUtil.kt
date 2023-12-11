package me.adriianhdev.plutotems.common.util

import com.cryptomorin.xseries.XItemStack
import me.adriianhdev.plutotems.common.util.EntityUtil.getPoseVector
import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.*
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import taboolib.module.configuration.Configuration

fun getTotemItem(config: Configuration, key: String) =
    config.getConfigurationSection("$key.item")!!.let { item ->
        XItemStack.deserialize(item.toMap()) { it.colorify() }
    }

private fun getItemStack(config: Configuration, path: String): ItemStack? {
    return config.getConfigurationSection(path)?.let { item ->
        XItemStack.deserialize(item.toMap()) { it.colorify() }
    }
}

fun getConditions(config: Configuration, key: String) = Condition(
    config.getInt("$key.conditions.chance.value"),
    config.getString("$key.conditions.permission.value"),
    config.getStringList("$key.conditions.kether")
)

fun getOptions(config: Configuration, key: String) = Options(
    config.getDouble("$key.options.health"),
    config.getBoolean("$key.options.playAnimation"),
    config.getInt("$key.options.cooldown"),
    config.getBoolean("$key.options.autoTotem"),
    config.getBoolean("$key.options.isClickable"),
    config.getBoolean("$key.options.isConsumable"),
    config.getBoolean("$key.options.isThrowable"),
    config.getBoolean("$key.options.isPickupable"),
    config.getBoolean("$key.options.isPlaceable")
)

fun getTypes(config: Configuration, key: String) = Types(
    config.getString("$key.types.type"),
    getTypesOptions(config, key),
    getEntityConfig(config, key),
    getTotemSchematic(config, key),
    getEntityOptions(config, key),
    config.getInt("$key.types.options.duration"),
    config.getDouble("$key.types.radius")
)

private fun getTypesOptions(config: Configuration, key: String) = TypesOptions(
    config.getString("$key.types.options.giveEffect"),
    config.getString("$key.types.options.executeActions"),
    config.getString("$key.types.options.executeScripts"),
    config.getBoolean("$key.types.options.entity.runOnKill")
)

private fun getTotemSchematic(config: Configuration, key: String) = TotemSchematic(
    config.getString("$key.types.options.schematic.name"),
    config.getDouble("$key.types.options.schematic.rotation.x"),
    config.getDouble("$key.types.options.schematic.rotation.y"),
    config.getDouble("$key.types.options.schematic.rotation.z"),
    config.getBoolean("$key.types.options.schematic.entities"),
    config.getBoolean("$key.types.options.schematic.air")
)

private fun getEntityOptions(config: Configuration, key: String) = EntityOptions(
    config.getBoolean("$key.types.options.entity.animation.play"),
    config.getDouble("$key.types.options.entity.animation.amplitude"),
    config.getDouble("$key.types.options.entity.animation.frequency"),
)

private fun getEntityConfig(config: Configuration, key: String): TotemEntity {
    val options = "$key.types.options.entity"

    val entityRotation = getEntityRotation(config, options)
    val entityPose = getEntityPose(config, options)
    val entityEquipment = getEntityEquipment(config, options)

    return TotemEntity(
        type = config.getString("$options.type"),
        name = config.getString("$options.name"),
        isNameVisible = config.getBoolean("$options.nameVisible"),
        hasAI = config.getBoolean("$options.ai"),
        isCollidable = config.getBoolean("$options.collidable"),
        hasGravity = config.getBoolean("$options.gravity"),
        isInvulnerable = config.getBoolean("$options.invulnerable"),
        isVisible = config.getBoolean("$options.visible"),
        rotation = entityRotation,
        pose = entityPose,
        equipment = entityEquipment,
        hasArms = config.getBoolean("$options.arms"),
        hasBasePlate = config.getBoolean("$options.basePlate"),
        isSmall = config.getBoolean("$options.small"),
        isMarker = config.getBoolean("$options.marker"),
        isGlowing = config.getBoolean("$options.glow")
    )
}

private fun getEntityRotation(config: Configuration, options: String): EntityRotation {
    val direction = Vector(
        config.getDouble("$options.rotation.direction.x"),
        config.getDouble("$options.rotation.direction.y"),
        config.getDouble("$options.rotation.direction.z"),
    )
    return EntityRotation(
        config.getDouble("$options.rotation.yaw").toFloat(),
        config.getDouble("$options.rotation.pitch").toFloat(),
        direction
    )
}

private fun getEntityPose(config: Configuration, options: String): EntityPose {
    return EntityPose(
        getPoseVector(config, "$options.pose.head"),
        getPoseVector(config, "$options.pose.body"),
        getPoseVector(config, "$options.pose.leftArm"),
        getPoseVector(config, "$options.pose.rightArm"),
        getPoseVector(config, "$options.pose.leftLeg"),
        getPoseVector(config, "$options.pose.rightLeg"),
    )
}

private fun getEntityEquipment(config: Configuration, options: String): EntityEquipment {
    return EntityEquipment(
        helmet = getItemStack(config, "$options.equipment.helmet"),
        chestplate = getItemStack(config, "$options.equipment.chestplate"),
        leggings = getItemStack(config, "$options.equipment.leggings"),
        boots = getItemStack(config, "$options.equipment.boots"),
        mainHand = getItemStack(config, "$options.equipment.mainHand"),
        offHand = getItemStack(config, "$options.equipment.offHand")
    )
}