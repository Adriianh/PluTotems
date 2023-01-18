package me.adriianhdev.plutotems.module.conf.totem

import org.bukkit.inventory.ItemStack

object TotemManager {
    val totems = mutableListOf<Totem>()

    fun addTotem(totem: Totem) {
        if (totems.contains(totem)) return
        totems.add(totem)
    }

    fun getTotem(id: String): Totem? {
        return totems.firstOrNull { it.id == id }
    }

    fun getTotem(item: ItemStack): Totem? {
        return totems.find { it.item == item }
    }
}