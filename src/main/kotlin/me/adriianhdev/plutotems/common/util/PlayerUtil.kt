package me.adriianhdev.plutotems.common.util

import me.adriianhdev.plutotems.common.util.StringUtil.replaceVar
import me.adriianhdev.plutotems.module.conf.totem.Totem
import org.bukkit.entity.Player
import taboolib.library.xseries.XItemStack

object PlayerUtil {
    fun giveTotem(player: Player, totem: Totem) {
        val totemItem = totem.item

        totemItem.itemMeta?.let { itemMeta ->
            if (itemMeta.hasLore()) {
                val lore = itemMeta.lore

                for (i in lore!!.indices) {
                    lore[i] = lore[i].replaceVar(totem)
                }

                itemMeta.lore = lore
            }
            totemItem.itemMeta = itemMeta
            TotemUtil.setKey(totemItem, totem)

            XItemStack.giveOrDrop(player, totemItem)
        }
    }

    fun removeTotem(player: Player, totem: Totem) {
        val inventory = player.inventory

        for (item in inventory.contents) {
            if (item == null) continue

            if (TotemUtil.getKey(item).equals(totem.id)) {
                item.amount--
                break
            }
        }
    }

    fun hasTotem(player: Player): Boolean {
        val offhand = player.inventory.itemInOffHand
        val hand = player.inventory.itemInMainHand

        if (TotemUtil.isTotem(offhand)) {
            return true
        } else {
            if (TotemUtil.isTotem(hand)) {
                return true
            }
        }
        return false
    }
}