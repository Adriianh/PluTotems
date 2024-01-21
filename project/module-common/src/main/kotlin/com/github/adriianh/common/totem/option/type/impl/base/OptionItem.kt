package com.github.adriianh.common.totem.option.type.impl.base

import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify
import org.bukkit.inventory.ItemStack
import taboolib.library.configuration.ConfigurationSection
import taboolib.library.xseries.XItemStack
import taboolib.library.xseries.XMaterial

class OptionItem : OptionBase<ItemStack>() {
    override val id: String = "ITEM"
    override val description: List<String> = listOf("Totem's item")
    override val optional: Boolean = false

    private var item: ItemStack = XMaterial.TOTEM_OF_UNDYING.parseItem()!!

    override fun isTypeCompatible(value: ItemStack): Boolean = true

    override fun getDefaultValue(): ItemStack = item

    override fun getExampleValue(): ItemStack = item

    override fun setOptionValue(value: ItemStack) {
        item = value
    }

    override fun getOptionValue(): ItemStack {
        return item
    }

    override fun getConvertedValue(value: Any?): ItemStack {
        val section = value as ConfigurationSection

        return XItemStack.deserialize(section.getValues(false))
    }

    override fun getMaterial(): XMaterial = XMaterial.TOTEM_OF_UNDYING

    override fun getItemName(): String = "&aItem".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's item
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}