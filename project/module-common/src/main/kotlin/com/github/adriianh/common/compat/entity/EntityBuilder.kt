package com.github.adriianh.common.compat.entity

import org.bukkit.Location
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.jetbrains.annotations.NotNull
import java.util.function.Consumer

@Suppress("unused")
class EntityBuilder private constructor(private val entity: LivingEntity) {
    fun hasGravity(gravity: Boolean): EntityBuilder {
        entity.setGravity(gravity)
        return this
    }

    fun name(name: String?): EntityBuilder {
        entity.customName = name
        return this
    }

    fun hasAI(ai: Boolean): EntityBuilder {
        entity.setAI(ai)
        return this
    }

    fun isNameVisible(flag: Boolean): EntityBuilder {
        entity.isCustomNameVisible = flag
        return this
    }

    fun isGlowing(flag: Boolean): EntityBuilder {
        entity.isGlowing = flag
        return this
    }

    fun isInvulnerable(flag: Boolean): EntityBuilder {
        entity.isInvulnerable = flag
        return this
    }

    fun isCollidable(collidable: Boolean): EntityBuilder {
        entity.isCollidable = collidable
        return this
    }

    fun isInvisible(invisible: Boolean): EntityBuilder {
        entity.isInvisible = invisible
        return this
    }

    fun addPassenger(passenger: Entity?): EntityBuilder {
        entity.addPassenger(passenger ?: return this)
        return this
    }

    fun addPotionEffect(@NotNull effect: PotionEffect?): EntityBuilder {
        entity.addPotionEffect(effect!!)
        return this
    }

    fun setHealth(health: Double): EntityBuilder {
        entity.health = (entity.health + health).coerceAtMost(
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue
        )
        return this
    }

    fun setAbsorption(amount: Double): EntityBuilder {
        entity.absorptionAmount = amount
        return this
    }

    fun add(consumer: Consumer<LivingEntity?>): EntityBuilder {
        consumer.accept(entity)
        return this
    }

    fun <T : LivingEntity?> add(clazz: Class<T>?, consumer: Consumer<T>): EntityBuilder {
        consumer.accept(entity as T)
        return this
    }

    fun <T : LivingEntity?> build(): T {
        return entity as T
    }

    companion object {
        fun asType(type: EntityType?, location: Location): EntityBuilder {
            return EntityBuilder(location.world?.spawnEntity(location, type!!) as LivingEntity)
        }
    }
}