package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.execute
import com.github.adriianh.common.totem.TotemFactory
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.cbool
import taboolib.module.ai.navigationMove

object EntityEvents {
    @SubscribeEvent
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        val player = entity.killer ?: return

        if (!TotemFactory.isTotem(entity)) return
        if (TotemFactory.isOwner(player, entity)) return

        val totem = TotemFactory.getTotem(entity)
        val option = totem.getOption("ENTITYKILL") ?: return

        if (!option.getOptionValue().cbool) return

        execute(player, totem)
    }

    @SubscribeEvent
    fun onPlayerDamageEntity(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        val player = event.damager as? Player ?: return

        if (!TotemFactory.isTotem(entity)) return
        if (!TotemFactory.isOwner(player, entity)) return

        event.isCancelled = true
    }

    @SubscribeEvent
    fun onEntityDamagePlayer(event: EntityDamageByEntityEvent) {
        val player = event.entity as? Player ?: return
        val entity = event.damager

        if (!TotemFactory.isTotem(entity)) return
        if (!TotemFactory.isOwner(player, entity)) return

        event.isCancelled = true
    }

    @SubscribeEvent
    fun onEntityTargetPlayer(event: EntityTargetLivingEntityEvent) {
        val player = event.target as? Player ?: return
        val entity = event.entity

        if (entity.type == EntityType.EXPERIENCE_ORB) return
        if (!TotemFactory.isTotem(entity)) return
        if (!TotemFactory.isOwner(player, entity as LivingEntity)) return

        entity.navigationMove(player.location)
        event.isCancelled = true
    }
}