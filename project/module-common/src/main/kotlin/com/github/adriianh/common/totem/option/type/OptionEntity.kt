package com.github.adriianh.common.totem.option.type

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.ItemRepresentation
import com.github.adriianh.common.totem.option.Option

abstract class OptionEntity<Any>(type: OptionTypes) : Option<Any>(type) {
    abstract override val id: String
    abstract override val description: List<String>
    abstract override val optional: Boolean

    override val identifier: String
        get() = id

    abstract override fun isTypeCompatible(value: Any): Boolean

    abstract override fun getDefaultValue(): kotlin.Any

    abstract override fun getExampleValue(): kotlin.Any

    abstract override fun getMaterial(): XMaterial

    abstract override fun getItemName(): String

    abstract override fun getItemLore(): List<String>

    override fun getItem() = ItemRepresentation.asItem(getMaterial())
}