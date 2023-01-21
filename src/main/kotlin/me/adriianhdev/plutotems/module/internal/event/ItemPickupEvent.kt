package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import taboolib.common.platform.event.SubscribeEvent

object ItemPickupEvent {
    @SubscribeEvent
    fun onItemPickup(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val world = player.world
        val item = event.item.itemStack
        val loc = event.item.location

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item) ?: return
        val option = totem.data.options

        if (!option.isPickupable!!) return
        if (!TotemUtil.checkCondition(player, totem)) return
        event.isCancelled = true

        if (item.amount > 1) {
            item.amount--

            event.item.remove()
            world.dropItem(loc, item)
            TotemUtil.run(player, totem)
        } else {
            event.item.remove()
            TotemUtil.run(player, totem)
        }
    }
}