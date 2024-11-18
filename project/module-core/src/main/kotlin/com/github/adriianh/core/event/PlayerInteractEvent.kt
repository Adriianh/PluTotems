package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.handleEvent
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

object PlayerInteractEvent {
    @SubscribeEvent(EventPriority.MONITOR)
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        handleEvent(player, item, "clickable")
    }
}