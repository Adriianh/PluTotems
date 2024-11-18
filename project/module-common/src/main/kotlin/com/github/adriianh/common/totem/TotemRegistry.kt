package com.github.adriianh.common.totem

import org.bukkit.inventory.ItemStack

object TotemRegistry {
    val totems = mutableMapOf<String, Totem>()

    fun registerTotem(name: String, loader: () -> Totem) {
        totems[name] = loader()
    }

    fun registerTotem(totem: Totem) {
        totems[totem.id] = totem
    }

    fun getTotem(identifier: String): Totem? {
        return totems[identifier]
    }

    fun getTotem(item: ItemStack): Totem? {
        return totems.values.find { totem ->
            totem.item.isSimilar(item)
        }
    }

    fun getTotems(): List<Totem> {
        return totems.values.toList()
    }
}