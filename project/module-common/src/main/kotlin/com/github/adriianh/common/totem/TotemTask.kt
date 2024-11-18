package com.github.adriianh.common.totem

import com.github.adriianh.common.totem.effect.EffectRegistry.addPotionEffect
import com.github.adriianh.common.util.ItemUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object TotemTask : Runnable {
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            val inventory = player.inventory.contents
            val hand = player.inventory.itemInMainHand
            val offhand = player.inventory.itemInOffHand

            if (!ItemUtil.isAirOrNull(hand)) {
                val item = player.inventory.itemInMainHand

                giveEffects(player, item)
            }
            if (!ItemUtil.isAirOrNull(offhand)) {
                val item = player.inventory.itemInOffHand

                giveEffects(player, item)
            }
            if (inventory.isNotEmpty()) {
                val armor: Array<ItemStack> = player.inventory.armorContents
                for (item in armor) {
                    if (ItemUtil.isAirOrNull(item)) continue

                    giveEffects(player, item)
                }
            }
        }
    }

    private fun giveEffects(player: Player, item: ItemStack) {
        if (!TotemFactory.isTotem(item)) return

        val totem = TotemFactory.getTotem(item)

        if (totem.type != TotemType.ARMOR) return

        val effects = totem.getEffects()
        if (effects.isEmpty()) return

        effects.forEach { effect ->
            addPotionEffect(player, effect)
        }
    }
}