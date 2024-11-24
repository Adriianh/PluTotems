package com.github.adriianh.common.totem

import com.cryptomorin.xseries.XItemStack
import com.jeff_media.morepersistentdatatypes.DataType
import org.bukkit.NamespacedKey
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import taboolib.platform.BukkitPlugin

object TotemFactory {
    private val plugin = BukkitPlugin.getInstance()
    private val key: NamespacedKey = NamespacedKey(plugin, "Totem")

    private fun setTotemData(item: ItemStack, totem: Totem) {
        val itemMeta = item.itemMeta!!

        itemMeta.persistentDataContainer.set(key, PersistentDataType.STRING, totem.id)

        item.setItemMeta(itemMeta)
    }

    fun setTotemData(entity: Entity, totem: Totem) {
        entity.persistentDataContainer.set(key, PersistentDataType.STRING, totem.id)
    }

    fun setOwner(player: Player, entity: Entity) {
        entity.persistentDataContainer.set(
            key,
            DataType.UUID,
            player.uniqueId
        )
    }

    fun isTotem(item: ItemStack): Boolean {
        return item.itemMeta!!.persistentDataContainer.has(key, PersistentDataType.STRING)
    }

    fun isTotem(entity: Entity): Boolean {
        return entity.persistentDataContainer.has(key, PersistentDataType.STRING)
    }

    fun getTotem(item: ItemStack): Totem {
        val id = item.itemMeta!!.persistentDataContainer.get(key, PersistentDataType.STRING)!!
        return TotemRegistry.getTotem(id)!!
    }

    fun getTotem(entity: Entity): Totem {
        val id = entity.persistentDataContainer.get(key, PersistentDataType.STRING)!!
        return TotemRegistry.getTotem(id)!!
    }

    fun getOwner(entity: Entity): Player {
        val uuid = entity.persistentDataContainer.get(key, DataType.UUID)!!
        return entity.world.players.find { it.uniqueId == uuid }!!
    }

    fun isOwner(player: Player, entity: Entity): Boolean {
        return entity.persistentDataContainer.get(key, DataType.UUID) == player.uniqueId
    }

    fun giveTotem(player: Player, totem: Totem, amount: Int = 1) {
        val item = totem.item.clone()

        item.amount = amount
        setTotemData(item, totem)
        XItemStack.giveOrDrop(player, item)
    }
}