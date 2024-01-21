package com.github.adriianh.common.totem

import org.bukkit.inventory.ItemStack
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.ItemBuilder

object ItemRepresentation {
    fun asItem(material: XMaterial): ItemStack {
        return ItemBuilder(material.parseMaterial()!!).build()
    }
}