package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.event.player.PlayerDropItemEvent
import taboolib.common.platform.event.SubscribeEvent

object ItemDropEvent {
    @SubscribeEvent
    fun onItemDrop(event: PlayerDropItemEvent) {
        val player = event.player
        val item = event.itemDrop.itemStack

        if(!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item)!!
        val option = totem.data.options

        if (!totem.type.equals("Item")) return
        if (option.isThrowable!!) {
            event.isCancelled = true

            if (!TotemUtil.checkCondition(player, totem)) {
                item.amount--
                return
            }

            TotemUtil.run(player, totem, item)
        }
    }
}