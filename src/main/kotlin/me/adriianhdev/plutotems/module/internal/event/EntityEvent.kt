package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils
import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityTargetLivingEntityEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.ai.navigationMove
import taboolib.platform.util.sendLang

object EntityEvent {
    @SubscribeEvent
    fun onEntityDeath(event: EntityDeathEvent) {
        val entity = event.entity
        val player = entity.killer ?: return

        if (!TotemUtil.isTotem(entity)) return
        val totem = TotemUtil.getTotem(entity) ?: return

        if (totem.data.types.options.runOnKill == false) return
        if (TotemUtil.isOwner(entity, player.uniqueId)) return
        if (!ExecutorUtils.checkCondition(player, totem)) return

        ExecutorUtils.run(player, totem)
    }

    @SubscribeEvent
    fun onPlayerDamageEntity(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        val player = event.damager as? Player ?: return

        if (!TotemUtil.isTotem(entity)) return
        if (!TotemUtil.isOwner(entity, player.uniqueId)) return

        event.isCancelled = true
        player.sendLang("Totem-Entity-Protect")
    }

    @SubscribeEvent
    fun onEntityDamagePlayer(event: EntityDamageByEntityEvent) {
        val player = event.entity as? Player ?: return
        val entity = event.damager

        if (!TotemUtil.isTotem(entity)) return
        if (!TotemUtil.isOwner(entity, player.uniqueId)) return

        event.isCancelled = true
    }

    @SubscribeEvent
    fun onEntityTargetPlayer(event: EntityTargetLivingEntityEvent) {
        val player = event.target as? Player ?: return
        val entity = event.entity as LivingEntity

        if (!TotemUtil.isTotem(entity)) return
        if (!TotemUtil.isOwner(entity, player.uniqueId)) return

        entity.navigationMove(player.location, 1.0)
        event.isCancelled = true
    }
}