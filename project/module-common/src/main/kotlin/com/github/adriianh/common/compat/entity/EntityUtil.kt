package com.github.adriianh.common.compat.entity

import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector
import taboolib.library.configuration.ConfigurationSection

data class EntityRotation(
    val yaw: Float = 0f,
    val pitch: Float = 0f,
    val direction: Vector = Vector()
)

data class EntityPose(
    val head: Vector = Vector(),
    val body: Vector = Vector(),
    val leftArm: Vector = Vector(),
    val rightArm: Vector = Vector(),
    val leftLeg: Vector = Vector(),
    val rightLeg: Vector = Vector()
)

data class EntityEquipment(
    val helmet: ItemStack? = null,
    val chestplate: ItemStack? = null,
    val leggings: ItemStack? = null,
    val boots: ItemStack? = null,
    val hand: ItemStack? = null,
    val offhand: ItemStack? = null
)

class EntityAnimation(
    private val entity: ArmorStand,
    private val amplitude: Double,
    private val frequency: Double,
) : BukkitRunnable() {
    private var currentTick: Long = 0
    private var lastYoffset: Double = 0.0

    override fun run() {
        if (entity.isDead) {
            cancel()
            return
        }

        val yOffset = Math.sin(currentTick * frequency) * amplitude
        entity.teleport(entity.location.add(0.0, yOffset - lastYoffset, 0.0))

        lastYoffset = yOffset
        currentTick++
    }
}

fun moveEntity(player: Player, entity: ArmorStand) {
    val location = player.location
    val direction = location.direction.multiply(Vector(
        1,
        1,
        -1
    )).normalize()

    entity.teleport(location.add(direction))

}

fun getVector(config: ConfigurationSection, path: String): Vector {
    return config.getString(path)?.let {
        val vector = it.split(",").map { it.trim().toDouble() }
        if (vector.size == 3) {
            Vector(vector[0], vector[1], vector[2])
        } else {
            Vector()
        }
    } ?: Vector()
}

fun Vector.asEulerAngle(): EulerAngle {
    return EulerAngle(this.x, this.y, this.z)
}