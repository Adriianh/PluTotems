package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils
import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.common.util.ExecutorUtils.run
import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.event.player.PlayerItemConsumeEvent
import taboolib.common.platform.event.SubscribeEvent

import taboolib.platform.util.sendLang

object ItemConsumeEvent {
    @SubscribeEvent
    fun onItemConsumed(event: PlayerItemConsumeEvent) {
        val player = event.player
        val item = event.item

        if (!PlayerUtil.hasTotem(player)) return
        val totem = TotemUtil.getTotem(item)!!
        val option = totem.data.options

        if (!totem.type.equals("item", true)) return
        if (!option.isConsumable!!) {
            player.sendLang("Totem-Non-Consumable")
            event.isCancelled = true
            return
        }

        if (!ExecutorUtils.checkCondition(player, totem)) {
            item.amount--
            return
        }

        checkEffects(player, item)
        run(player, totem)
        item.amount--
    }
}