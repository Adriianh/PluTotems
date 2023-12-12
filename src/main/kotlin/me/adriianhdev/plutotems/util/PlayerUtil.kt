package me.adriianhdev.plutotems.util

import me.adriianhdev.plutotems.core.totem.Totem
import me.adriianhdev.plutotems.util.StringUtil.replaceVar
import org.bukkit.entity.Player
import taboolib.library.xseries.XItemStack

object PlayerUtil {
    fun giveTotem(player: Player, totem: Totem) {
        val totemItem = totem.item.clone()

        totemItem.itemMeta.let { it ->
            it?.lore = it?.lore?.map { it.replaceVar(totem) }

            totemItem.itemMeta = it
            TotemUtil.setKey(totemItem, totem)

            XItemStack.giveOrDrop(player, totemItem)
        }
    }

    fun giveTotem(player: Player, totem: Totem, amount: Int) {
        val totemItem = totem.item.clone()

        val totemMeta = totemItem.itemMeta
        totemMeta.let { it ->
            it?.lore = it?.lore?.map { it.replaceVar(totem) }
        }

        totemItem.itemMeta = totemMeta
        totemItem.amount = amount
        TotemUtil.setKey(totemItem, totem)

        XItemStack.giveOrDrop(player, totemItem)
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

    fun getNearbyPlayers(player: Player, radius: Double): List<Player> {
        val nearbyPlayers = mutableListOf<Player>()

        player.getNearbyEntities(radius, radius, radius)
            .filterIsInstance<Player>()
            .forEach { nearbyPlayers.add(it) }

        return nearbyPlayers
    }
}