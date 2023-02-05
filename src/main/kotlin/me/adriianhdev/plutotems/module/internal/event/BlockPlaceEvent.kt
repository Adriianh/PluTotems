package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkCondition
import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.common.util.ExecutorUtils.run
import me.adriianhdev.plutotems.common.util.ExecutorUtils.runType
import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.event.block.BlockPlaceEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang
import java.util.*

object BlockPlaceEvent {
    @SubscribeEvent
    fun onBlockPlace(event: BlockPlaceEvent) {
        val player = event.player
        val item = event.itemInHand

        if (!PlayerUtil.hasTotem(player)) return
        val totem = TotemUtil.getTotem(item) ?: return
        val type = totem.data.types
        val option = totem.data.options

        event.isCancelled = true

        if (!type.type.equals("item", true)) return
        if (!option.isPlaceable!!) {
            player.sendLang("Totem-Non-Placeable")
            return
        }

        if (!checkCondition(player, totem)) {
            event.isCancelled = true
            item.amount--

            return
        }

        checkEffects(player, item)
        run(player, totem)
        item.amount--
    }

    @SubscribeEvent
    fun onTypePlace(event: BlockPlaceEvent) {
        val player = event.player
        val item = event.itemInHand

        if (!PlayerUtil.hasTotem(player)) return
        val totem = TotemUtil.getTotem(item) ?: return
        val type = totem.data.types.type
        val option = totem.data.options

        event.isCancelled = true

        if (!option.isPlaceable!!) {
            player.sendLang("Totem-Non-Placeable")
            return
        }

        if (!checkCondition(player, totem)) {
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