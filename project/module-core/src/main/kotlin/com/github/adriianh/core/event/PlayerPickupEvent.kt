package com.github.adriianh.core.event

import com.github.adriianh.common.totem.TotemExecutor.handleEvent
import com.github.adriianh.common.totem.TotemFactory
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common5.cbool

object PlayerPickupEvent {
    @SubscribeEvent
    fun onPlayerPickup(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val world = player.world
        val item = event.item.itemStack
        val loc = event.item.location

        if (!TotemFactory.isTotem(item)) return
        event.isCancelled = true

        val totem = TotemFactory.getTotem(item)
        val option = totem.getOption("pickupable") ?: return

        if (!option.getOptionValue().cbool) return
        handleEvent(player, item, "pickupable", event, true, false)
        player.playSound(player.location, "entity.item.pickup", 1f, 1f)

        if (item.amount > 1) {
            item.amount--

            event.item.remove()
            world.dropItem(loc, item)
        } else {
            event.item.remove()
        }
    }
}