package com.github.adriianh.common.totem

import org.bukkit.inventory.ItemStack

object TotemRegistry {
    val totems = mutableMapOf<String, com.github.adriianh.common.totem.Totem>()

    fun registerTotem(name: String, loader: () -> com.github.adriianh.common.totem.Totem) {
        totems[name] = loader()
    }

    fun registerTotem(totem: com.github.adriianh.common.totem.Totem) {
        totems[totem.id] = totem
    }

    fun getTotem(identifier: String): com.github.adriianh.common.totem.Totem? {
        return totems[identifier]
    }

    fun getTotem(item: ItemStack): com.github.adriianh.common.totem.Totem? {
        return totems.values.find { totem ->
            totem.item.isSimilar(item)
        }
    }

    fun getTotems(): List<com.github.adriianh.common.totem.Totem> {
        return totems.values.toList()
    }
}