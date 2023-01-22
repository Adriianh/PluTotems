package me.adriianhdev.plutotems.module.internal.task

import me.adriianhdev.plutotems.common.util.TotemUtil
import me.adriianhdev.plutotems.common.util.TotemUtil.isAirOrNull
import me.adriianhdev.plutotems.module.conf.totem.effect.Effect
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack


object EffectTask : Runnable {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val inventory = player.inventory.contents
            val hand = player.inventory.itemInMainHand
            val offhand = player.inventory.itemInOffHand

            if (!isAirOrNull(hand)) {
                val item = player.inventory.itemInMainHand

                giveEffect(player, item)
            }
            if (!isAirOrNull(offhand)) {
                val item = player.inventory.itemInOffHand

                giveEffect(player, item)
            }
            if (inventory.isNotEmpty()) {
                val armor: Array<ItemStack> = player.inventory.armorContents
                for (item in armor) {
                    if (isAirOrNull(item)) continue

                    giveEffect(player, item)
                }
            }
        }
    }

    private fun giveEffect(player: Player, item: ItemStack) {
        if (!TotemUtil.isTotem(item)) return

        val totem = TotemUtil.getTotem(item)!!
        val effects = totem.data.heldEffect ?: return

        if (effects.isNotEmpty()) {
            effects.forEach { effect ->
                Effect.addEffect(effect, player)
            }
        }
    }
}