package com.github.adriianh.common.totem

import com.github.adriianh.common.compat.entity.EntityAnimation
import com.github.adriianh.common.compat.entity.EntityFactory
import com.github.adriianh.common.compat.entity.moveEntity
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.totem.option.type.impl.base.OptionAnimation
import com.github.adriianh.common.totem.option.type.impl.base.OptionDuration
import com.github.adriianh.common.totem.option.type.impl.entity.OptionEntityType
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import taboolib.platform.BukkitPlugin
import taboolib.platform.util.sendLang
import java.util.*

object TotemEntityFactory {
    private val entities = EnumMap<EntityType, Entity>(EntityType::class.java)

    fun spawnEntity(player: Player, totem: Totem) {
        val location = player.location
        val duration = totem.getOption("duration") as OptionDuration
        val type = (totem.getOption("type", OptionTypes.ENTITY) as? OptionEntityType)?.getOptionValue()
            ?: return player.sendLang("Totem-Non-Entity")

        val entity = when (type) {
            EntityType.ARMOR_STAND -> {
                EntityFactory.spawnArmorStandEntity(type, location, totem)
            }

            else -> {
                EntityFactory.spawnMobEntity(type, location, totem)
            }
        }

        entities[type] = entity!!

        TotemFactory.setTotemData(entity, totem)
        TotemFactory.setOwner(player, entity)

        if (entity.type == EntityType.ARMOR_STAND) {
            moveAndAnimateEntity(player, entity as ArmorStand, totem)
        }

        removeEntityAfterDelay(type, duration.getOptionValue())
    }

    private fun moveAndAnimateEntity(player: Player, entity: ArmorStand, totem: Totem) {
        object : BukkitRunnable() {
            override fun run() {
                if (entity.isDead) {
                    cancel()
                    return
                }

                moveEntity(player, entity)
            }
        }.runTaskTimer(BukkitPlugin.getInstance(), 0, 1)

        val animation = totem.getOption("animation") as? OptionAnimation ?: return
        if (animation.getOptionValue()) {
            EntityAnimation(entity, 1.0, 9.0)
                .runTaskTimer(BukkitPlugin.getInstance(), 0, 1)
        }
    }

    private fun removeEntityAfterDelay(entityType: EntityType, delay: Int) {
        object : BukkitRunnable() {
            override fun run() {
                val removedEntity = entities.remove(entityType)
                removedEntity?.remove()
            }
        }.runTaskLater(BukkitPlugin.getInstance(), (delay * 20).toLong())
    }

    fun removeEntities() {
        if (entities.isEmpty()) return

        entities.values.forEach(Entity::remove)
        entities.clear()
    }
}