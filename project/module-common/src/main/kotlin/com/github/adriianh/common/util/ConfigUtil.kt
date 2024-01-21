package com.github.adriianh.common.util

import org.bukkit.inventory.ItemStack
import taboolib.library.configuration.ConfigurationSection
import taboolib.library.xseries.XItemStack

object ConfigUtil {
    fun getItemStack(config: ConfigurationSection, path: String): ItemStack? {
        return config.getConfigurationSection(path)?.let { item ->
            XItemStack.deserialize(item.toMap()) { it.colorify() }
        }
    }
}