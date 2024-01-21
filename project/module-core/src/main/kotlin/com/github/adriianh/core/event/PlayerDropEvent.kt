package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.handleEvent
import org.bukkit.event.player.PlayerDropItemEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerDropEvent {
    @SubscribeEvent
    fun onPlayerDrop(event: PlayerDropItemEvent) {
        val player = event.player
        val item = event.itemDrop.itemStack

        handleEvent(player, item, "throwable")
    }
}