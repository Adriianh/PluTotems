package me.adriianhdev.plutotems.core.event

import me.adriianhdev.plutotems.util.ExecutorUtils.handleAction
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

object PlayerDamageEvent {
    @SubscribeEvent(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        val player: Player = event.entity as Player

        if (event.finalDamage >= player.health) {
            handleAction(player, event)
        }
    }
}