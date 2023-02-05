package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils
import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.common.util.ExecutorUtils.run
import me.adriianhdev.plutotems.common.util.ExecutorUtils.runType
import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.event.player.PlayerItemConsumeEvent
import taboolib.common.platform.event.SubscribeEvent

import taboolib.platform.util.sendLang
import java.util.*

object ItemConsumeEvent {
    @SubscribeEvent
    fun onItemConsumed(event: PlayerItemConsumeEvent) {
        val player = event.player
        val item = event.item

        if (!PlayerUtil.hasTotem(player)) return
        val totem = TotemUtil.getTotem(item)!!
        val type = totem.data.types
        val option = totem.data.options

        if (!type.type.equals("item", true)) return
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

    @SubscribeEvent
    fun onTypeConsume(event: PlayerItemConsumeEvent) {
        val player = event.player
        val item = event.item

        if (!PlayerUtil.hasTotem(player)) return
        val totem = TotemUtil.getTotem(item)!!
        val type = totem.data.types.type
        val option = totem.data.options

        if (!option.isConsumable!!) {
            player.sendLang("Totem-Non-Consumable")
            event.isCancelled = true
            return
        }

        if (!ExecutorUtils.checkCondition(player, totem)) {
            item.amount--
            return
        }

        if (type != null) {
            when (type.lowercase(Locale.getDefault())) {
                "structure" -> {
                    checkEffects(player, item)
                    runType(player, totem)
                    item.amount--
                }
                "entity" -> {
                    checkEffects(player, item)
                    runType(player, totem)
                    item.amount--
                }
            }
        }
    }
}