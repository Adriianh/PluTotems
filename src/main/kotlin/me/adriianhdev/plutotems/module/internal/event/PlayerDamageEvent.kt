package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerDamageEvent {
    @SubscribeEvent
    fun onPlayerDamage(event: EntityDamageEvent) {
        val player = event.entity

        if (player !is Player) return
        if (!PlayerUtil.hasTotem(player)) return
        if (event.cause == EntityDamageEvent.DamageCause.SUICIDE) return

        if (event.finalDamage >= player.health) {
            TotemUtil.handleAction(player, event)
        }
    }
}