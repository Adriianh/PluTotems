package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.handleEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerConsumeEvent {
    @SubscribeEvent
    fun onPlayerConsume(event: PlayerItemConsumeEvent) {
        val player = event.player
        val item = event.item

        handleEvent(player, item, "consumable")
    }
}