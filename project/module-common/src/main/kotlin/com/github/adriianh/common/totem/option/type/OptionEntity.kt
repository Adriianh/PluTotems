package com.github.adriianh.common.totem.option.type

import com.github.adriianh.common.totem.ItemRepresentation
import com.github.adriianh.common.totem.option.Option
import taboolib.library.xseries.XMaterial

abstract class OptionEntity<Any> : Option<Any>() {
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