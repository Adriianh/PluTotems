package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.handleEvent
import org.bukkit.event.block.BlockPlaceEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerPlaceEvent {
    @SubscribeEvent
    fun onPlayerPlaceEvent(event: BlockPlaceEvent) {
        val player = event.player
        val item = event.itemInHand

        handleEvent(player, item, "placeable", event, true)
    }
}