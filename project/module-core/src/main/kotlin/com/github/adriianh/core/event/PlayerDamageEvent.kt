package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerDamageEvent {
    @SubscribeEvent
    fun onPlayerDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return

        val player: Player = event.entity as Player
        if (event.finalDamage >= player.health) {
            TotemExecutor.execute(player, event)
        }
    }
}