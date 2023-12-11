package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkCondition
import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.common.util.ExecutorUtils.run
import me.adriianhdev.plutotems.common.util.TotemUtil
import me.adriianhdev.plutotems.common.util.TotemUtil.checkType
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang

object ItemInteractEvent {
    @SubscribeEvent(EventPriority.MONITOR)
    fun onItemInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item)!!
        val option = totem.data.options

        if (option.isConsumable == true) return
        if (option.isPlaceable == true) return
        event.isCancelled = true

        if (!totem.data.types.type.equals("item", true)) return
        if (!option.isClickable!!) {
            player.sendLang("Totem-Non-Clickable")
            return
        }

        if (!checkCondition(player, totem)) {
            item.amount--
            return
        }

        checkEffects(player, item)
        run(player, totem)
        item.amount--
    }

    @SubscribeEvent
    fun onTypeInteract(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item)!!
        val option = totem.data.options
        val type = totem.data.types.type

        if (option.isConsumable == true) return
        if (option.isPlaceable == true) return
        event.isCancelled = true

        if (!option.isClickable!!) {
            player.sendLang("Totem-Non-Clickable")
            return
        }

        if (!checkCondition(player, totem)) {
            item.amount--
            return
        }

        if (type != null) {
            checkType(type, player, totem, item)
        }
    }
}