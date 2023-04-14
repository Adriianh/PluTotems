package me.adriianhdev.plutotems.common.util

import de.eldoria.eldoutilities.builder.EntityBuilder
import io.lumine.mythic.bukkit.BukkitAdapter
import io.lumine.mythic.bukkit.MythicBukkit
import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.Totem
import org.bukkit.Location
import org.bukkit.entity.*
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector
import taboolib.common.platform.function.info
import taboolib.platform.util.sendLang
import java.util.*
import kotlin.math.sin

data class TotemEntity(
    val type: String?,
    val name: String?,
    val isNameVisible: Boolean?,
    val hasAI: Boolean?,
    val isCollidable: Boolean?,
    val hasGravity: Boolean?,
    val isInvulnerable: Boolean?,
    val isVisible: Boolean?,
    val rotation: EntityRotation?,
    val pose: EntityPose?,
    val equipment: EntityEquipment? = null,
    val hasArms: Boolean?,
    val hasBasePlate: Boolean?,
    val isSmall: Boolean?,
    val isMarker: Boolean?,
    val isGlowing: Boolean?,
)

data class EntityRotation(
    val yaw: Float,
    val pitch: Float,
    val direction: Vector
)

data class EntityPose(
    val head: Vector? = null ?: Vector(0.0, 0.0, 0.0),
    val body: Vector? = null ?: Vector(0.0, 0.0, 0.0),
    val leftArm: Vector? = null ?: Vector(0.0, 0.0, 0.0),
    val rightArm: Vector? = null ?: Vector(0.0, 0.0, 0.0),
    val leftLeg: Vector? = null ?: Vector(0.0, 0.0, 0.0),
    val rightLeg: Vector? = null ?: Vector(0.0, 0.0, 0.0)
)

data class EntityEquipment(
    val helmet: ItemStack? = null,
    val chestplate: ItemStack? = null,
    val leggings: ItemStack? = null,
    val boots: ItemStack? = null,
    val mainHand: ItemStack? = null,
    val offHand: ItemStack? = null
)

class EntityAnimation(
    private val armorStand: ArmorStand,
    private val amplitude: Double,
    private val frequency: Double
):
    BukkitRunnable() {
        private var currentTick: Long = 0
        private var lastYOffset = 0.0
        override fun run() {
            if (armorStand.isDead) cancel()

            val yOffset = sin(Math.toRadians(currentTick * frequency)) * amplitude
            armorStand.teleport(armorStand.location.add(0.0, yOffset - lastYOffset, 0.0))

            lastYOffset = yOffset
            currentTick++
    }
}

object EntityUtil {
    private var entities: HashMap<String, Entity> = HashMap()

    fun spawnEntity(player: Player, totem: Totem) {
        val owner = player.uniqueId
        val duration = totem.data.types.duration
        val totemEntity = totem.data.types.entity ?: return player.sendLang("Totem-Non-Entity", totem.id)
        val entityType = totemEntity.type ?: return player.sendLang("Totem-Non-Entity", totem.id)
        val uuid = "${UUID.randomUUID()}:$entityType"

        val entity: Entity? = when {
            entityType.equals("armor_stand", true) -> {
                buildArmorStand(totemEntity, player.location)
            }
            entityType.startsWith("mm:", true) ->{
                buildMythicMob(entityType, player.location)
            }
            else -> {
                buildEntity(totemEntity, player.location)
            }
        }

        entities[uuid] = entity!!

        TotemUtil.setKey(entity, totem)
        TotemUtil.setOwner(entity, owner)

        if (entity.type == EntityType.ARMOR_STAND) {
            val entityWalk: BukkitRunnable = object : BukkitRunnable() {
                override fun run() {
                    moveEntity(player, entity as ArmorStand)
                }
            }
            entityWalk.runTaskTimer(PluTotems.plugin, 0, 1)

            if (totem.data.types.animation == true) {
                me.adriianhdev.plutotems.common.util.EntityAnimation(entity as ArmorStand, 1.0, 9.0)
                    .runTaskTimer(PluTotems.plugin, 0, 1)
            }
        }

        val removeRunnable: BukkitRunnable = object : BukkitRunnable() {
            override fun run() {
                entity.remove()
            }
        }
        removeRunnable.runTaskLater(PluTotems.plugin, duration * 20L)
    }

    fun removeEntity() {
        info("Removing all generated entities")
        info("Entities: ${entities.size}")

        try {
            entities.forEach { (_, entity) ->
                entity.remove()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error("An error occurred while removing entities")
        }

        entities.clear()
    }

    fun moveEntity(player: Player, armorStand: ArmorStand) {
        val direction = player.location.direction.multiply(Vector(1, 1, -1)).normalize()
        armorStand.teleport(player.location.add(direction))
    }

    private fun buildArmorStand(entity: TotemEntity, location: Location?): ArmorStand? {
        return EntityBuilder.of(EntityType.valueOf(entity.type!!), location)
            .withCustomName(entity.name?.colorify())
            .withVisibleCustomName(entity.isNameVisible!!)
            .setCollidable(entity.isCollidable!!)
            .withGravity(entity.hasGravity!!)
            .asInvulnerable(entity.isInvulnerable!!)
            .withGlowing(entity.isGlowing!!)
            .with { e: LivingEntity ->
                e.setRotation(entity.rotation!!.yaw, entity.rotation.yaw)
                e.location.setDirection(entity.rotation.direction)
            }
            .with(ArmorStand::class.java) { e: ArmorStand ->
                e.headPose = vectorToEulerAngle(entity.pose?.head!!)
                e.bodyPose = vectorToEulerAngle(entity.pose.body!!)
                e.leftArmPose = vectorToEulerAngle(entity.pose.leftArm!!)
                e.rightArmPose = vectorToEulerAngle(entity.pose.rightArm!!)
                e.leftLegPose = vectorToEulerAngle(entity.pose.leftLeg!!)
                e.rightLegPose = vectorToEulerAngle(entity.pose.rightLeg!!)
            }
            .with { e: LivingEntity ->
                val eq = e.equipment
                eq!!.helmet = entity.equipment!!.helmet
                eq.chestplate = entity.equipment.chestplate
                eq.leggings = entity.equipment.leggings
                eq.boots = entity.equipment.boots
                eq.setItemInMainHand(entity.equipment.mainHand)
                eq.setItemInOffHand(entity.equipment.offHand)
            }
            .with(ArmorStand::class.java) { e: ArmorStand ->
                e.setArms(entity.hasArms!!)
                e.setBasePlate(entity.hasBasePlate!!)
                e.isMarker = entity.isMarker!!
                e.isSmall = entity.isSmall!!
                e.isVisible = entity.isVisible!!
            }
            .build()
    }

    private fun buildEntity(entity: TotemEntity, location: Location?): Entity? {
        return EntityBuilder.of(EntityType.valueOf(entity.type!!), location)
            .withCustomName(entity.name?.colorify())
            .withVisibleCustomName(entity.isNameVisible!!)
            .setCollidable(entity.isCollidable!!)
            .withGravity(entity.hasGravity!!)
            .asInvulnerable(entity.isInvulnerable!!)
            .withGlowing(entity.isGlowing!!)
            .withAI(entity.hasAI!!)
            .with { e: LivingEntity ->
                e.setRotation(entity.rotation!!.yaw, entity.rotation.yaw)
                e.location.setDirection(entity.rotation.direction)
            }
            .with { e: LivingEntity ->
                val eq = e.equipment
                eq!!.helmet = entity.equipment!!.helmet
                eq.chestplate = entity.equipment.chestplate
                eq.leggings = entity.equipment.leggings
                eq.boots = entity.equipment.boots
                eq.setItemInMainHand(entity.equipment.mainHand)
                eq.setItemInOffHand(entity.equipment.offHand)
            }
            .build()
    }

    private fun buildMythicMob(entity: String, location: Location?): Entity {
        val id = entity.split("mm:")[1]
        val mob = MythicBukkit.inst().mobManager
            .getMythicMob(id)
            .orElse(null) ?: error ("MythicMob $id not found")

        val spawn = mob.spawn(BukkitAdapter.adapt(location), 1.0)

        return spawn.entity.bukkitEntity
    }

    private fun vectorToEulerAngle(vector: Vector): EulerAngle {
        return EulerAngle(vector.x, vector.y, vector.z)
    }
}