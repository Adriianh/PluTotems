package com.github.adriianh.common.totem

import com.cryptomorin.xseries.XMaterial
import org.bukkit.inventory.ItemStack
import taboolib.platform.util.ItemBuilder

object ItemRepresentation {
    fun asItem(material: XMaterial): ItemStack {
        return ItemBuilder(material.parseMaterial()!!).build()
    }
}