package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils.handleAction
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerDamageEvent {
    @SubscribeEvent
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return
        val player: Player = event.entity as Player

        if (event.finalDamage >= player.health) {
            handleAction(player, event)
        }
    }
}