package me.adriianhdev.plutotems.common.util

import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.module.conf.totem.Totem
import me.adriianhdev.plutotems.module.conf.totem.TotemManager
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType


object TotemUtil {
    private val key = NamespacedKey(PluTotems.plugin, "totem")

    fun setKey(item: ItemStack, totem: Totem) {
        val meta = item.itemMeta!!
        val container = meta.persistentDataContainer

        container.set(key, PersistentDataType.STRING, totem.id)
        item.itemMeta = meta
    }

    fun getKey(item: ItemStack): String? {
        val container: PersistentDataContainer = item.itemMeta!!.persistentDataContainer
        return container.get(key, PersistentDataType.STRING)
    }

    fun hasKey(item: ItemStack): Boolean {
        val container = item.itemMeta!!.persistentDataContainer
        return container.has(key, PersistentDataType.STRING)
    }

    fun isTotem(item: ItemStack): Boolean {
        return (item.hasItemMeta() && hasKey(item))
    }

    fun getTotem(item: ItemStack): Totem? {
        if (!hasKey(item)) return null
        val id = getKey(item)!!

        return TotemManager.getTotem(id)
    }

    fun isAirOrNull(item: ItemStack?): Boolean {
        return item == null || item.type == Material.AIR
    }
}