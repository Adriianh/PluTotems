package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkCondition
import me.adriianhdev.plutotems.common.util.ExecutorUtils.checkEffects
import me.adriianhdev.plutotems.common.util.ExecutorUtils.run
import me.adriianhdev.plutotems.common.util.ExecutorUtils.runType
import me.adriianhdev.plutotems.common.util.TotemUtil
import me.adriianhdev.plutotems.module.conf.totem.Totem
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent
import java.util.*

object ItemPickupEvent {
    @SubscribeEvent
    fun onItemPickup(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val world = player.world
        val item = event.item.itemStack
        val loc = event.item.location

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item) ?: return
        val type = totem.data.types
        val option = totem.data.options

        if (!type.type.equals("item", true)) return
        if (!option.isPickupable!!) return
        if (!checkCondition(player, totem)) return
        event.isCancelled = true

        if (item.amount > 1) {
            item.amount--

            event.item.remove()
            world.dropItem(loc, item)
            checkEffects(player, item)
            run(player, totem)
        } else {
            event.item.remove()
            checkEffects(player, item)
            run(player, totem)
        }
    }

    @SubscribeEvent
    fun onTypePickup(event: EntityPickupItemEvent) {
        val player = event.entity as? Player ?: return
        val world = player.world
        val item = event.item.itemStack
        val loc = event.item.location

        if (!TotemUtil.isTotem(item)) return
        val totem = TotemUtil.getTotem(item) ?: return
        val type = totem.data.types.type ?: return
        val option = totem.data.options

        if (!option.isPickupable!!) return
        if (!checkCondition(player, totem)) return
        event.isCancelled = true

        if (item.amount > 1) {
            item.amount--

            event.item.remove()
            world.dropItem(loc, item)
            checkType(type, player, totem, item)
        } else {
            event.item.remove()
            checkType(type, player, totem, item)
        }
    }

    private fun checkType(type: String, player: Player, totem: Totem, item: ItemStack) {
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