package me.adriianhdev.plutotems.core.event

import me.adriianhdev.plutotems.util.ExecutorUtils.checkCondition
import me.adriianhdev.plutotems.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.util.ExecutorUtils.run
import me.adriianhdev.plutotems.util.TotemUtil
import me.adriianhdev.plutotems.util.TotemUtil.checkType
import org.bukkit.event.player.PlayerDropItemEvent
import taboolib.common.platform.event.SubscribeEvent

object ItemDropEvent {
    @SubscribeEvent
    fun onItemDrop(event: PlayerDropItemEvent) {
        val player = event.player
        val item = event.itemDrop.itemStack

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item)!!
        val type = totem.data.types
        val option = totem.data.options

        if (!type.type.equals("item", true)) return
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

    @SubscribeEvent
    fun onTypeDrop(event: PlayerDropItemEvent) {
        val player = event.player
        val item = event.itemDrop.itemStack

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item)!!
        val type = totem.data.types.type
        val option = totem.data.options

        if (option.isThrowable!!) {
            event.isCancelled = true

            if (!checkCondition(player, totem)) {
                item.amount--
                return
            }

            if (type != null) {
                checkType(type, player, totem, item)
            }
        }
    }
}