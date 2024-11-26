package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.handleEvent
import com.github.adriianh.common.totem.TotemFactory
import org.bukkit.event.block.BlockPlaceEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerPlaceEvent {
    @SubscribeEvent
    fun onPlayerPlaceEvent(event: BlockPlaceEvent) {
        val player = event.player
        val item = event.itemInHand

        if (!TotemFactory.isTotem(item)) return
        event.isCancelled = true

        handleEvent(player, item, "placeable", event, true)
    }
}