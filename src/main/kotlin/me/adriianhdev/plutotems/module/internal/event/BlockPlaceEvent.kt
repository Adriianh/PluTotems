package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.common.util.TotemUtil
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
        val option = totem.data.options

        event.isCancelled = true

        if (!totem.type.equals("Item")) return
        if (!option.isPlaceable!!) {
            player.sendLang("Totem-Non-Placeable")
            return
        }

        if (!TotemUtil.checkCondition(player, totem)) {
            event.isCancelled = true
            item.amount--

            return
        }

        TotemUtil.run(player, totem, item)
    }
}