package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.handleEvent
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import taboolib.common.platform.event.SubscribeEvent

object PlayerPickupEvent {
    @SubscribeEvent
    fun onPlayerInteract(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val item = event.item.itemStack

        handleEvent(player, item, "pickupable", event, true)

        val isMultipleItems = item.amount > 1
        if (isMultipleItems) {
            item.amount--
        }

        event.item.remove()
        if (isMultipleItems) {
            player.world.dropItem(event.item.location, item)
        }
    }
}