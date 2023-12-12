package me.adriianhdev.plutotems.core.event

import me.adriianhdev.plutotems.util.ExecutorUtils.checkCondition
import me.adriianhdev.plutotems.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.util.ExecutorUtils.run
import me.adriianhdev.plutotems.util.PlayerUtil
import me.adriianhdev.plutotems.util.TotemUtil
import me.adriianhdev.plutotems.util.TotemUtil.checkType
import org.bukkit.event.block.BlockPlaceEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.sendLang

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
            checkType(type, player, totem, item)
        }
    }
}