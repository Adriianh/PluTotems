package com.github.adriianh.common.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ItemUtil {
    fun isAirOrNull(item: ItemStack?): Boolean {
        return item == null || item.type == Material.AIR || item.amount <= 0
    }
}