package me.adriianhdev.plutotems.module.internal.event

import me.adriianhdev.plutotems.common.util.PlayerUtil
import me.adriianhdev.plutotems.common.util.TotemUtil
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack
import taboolib.common.platform.event.SubscribeEvent

object PlayerDamageEvent {
    @SubscribeEvent
    fun onPlayerDamage(event: EntityDamageEvent) {
        val player = event.entity

        if (player !is Player) return
        if (!PlayerUtil.hasTotem(player)) return
        if (event.cause == EntityDamageEvent.DamageCause.SUICIDE) return

        if (event.finalDamage >= player.health) {
            handleAction(player, event)
        }
    }

    private fun handleAction(player: Player, event: Cancellable) {
        val offhand = player.inventory.itemInOffHand
        val hand = player.inventory.itemInMainHand

        if (TotemUtil.isTotem(offhand)) {
            check(offhand, player, event)
        } else {
            if (TotemUtil.isTotem(hand)) {
                check(hand, player, event)
            }
        }
    }

    private fun check(item: ItemStack, player: Player, event: Cancellable) {
        val totem = TotemUtil.getTotem(item)!!

        if (!TotemUtil.checkCondition(player, totem)) {
            event.isCancelled = false
            item.amount--

            return
        }

        event.isCancelled = true
        TotemUtil.run(player, totem, item)
    }
}