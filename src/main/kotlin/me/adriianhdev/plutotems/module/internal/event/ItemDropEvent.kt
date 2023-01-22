package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkCondition
import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.common.util.ExecutorUtils.run
import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.event.player.PlayerDropItemEvent
import taboolib.common.platform.event.SubscribeEvent

object ItemDropEvent {
    @SubscribeEvent
    fun onItemDrop(event: PlayerDropItemEvent) {
        val player = event.player
        val item = event.itemDrop.itemStack

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item)!!
        val option = totem.data.options

        if (!totem.type.equals("item", true)) return
        if (option.isThrowable!!) {
            event.isCancelled = true

            if (!checkCondition(player, totem)) {
                item.amount--
                return
            }

            checkEffects(player, item)
            run(player, totem)
            item.amount--
        }
    }
}