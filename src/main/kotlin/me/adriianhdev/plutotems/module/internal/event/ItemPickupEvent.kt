package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import taboolib.common.platform.event.SubscribeEvent

object ItemPickupEvent {
    @SubscribeEvent
    fun onItemPickup(event: EntityPickupItemEvent) {
        val player = event.entity
        val item = event.item.itemStack

        if (player !is Player) return
        if (!TotemUtil.isTotem(item)) return

        val totem = TotemUtil.getTotem(item)!!
        val option = totem.data.options

        if (!totem.type.equals("Item")) return
        if (option.isPickupable!!) return

        item.amount--
        event.isCancelled = true

        if (!TotemUtil.checkCondition(player, totem)) {
            item.amount--
            return
        }

        TotemUtil.run(player, totem, item)
    }
}