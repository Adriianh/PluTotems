package com.github.adriianh.common.totem.option

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.ItemRepresentation
import com.github.adriianh.common.totem.Property

abstract class Option<T> : Property, Cloneable {
    abstract val id: String
    abstract val description: List<String>
    abstract val optional: Boolean

    override val identifier: String
        get() = id

    abstract fun isTypeCompatible(value: T): Boolean

    abstract fun getDefaultValue(): Any

    abstract fun getExampleValue(): Any

    abstract fun setOptionValue(value: T)

    abstract fun getOptionValue(): Any

    abstract fun getConvertedValue(value: Any?): T

    abstract fun getMaterial(): XMaterial

    abstract fun getItemName(): String

    abstract fun getItemLore(): List<String>

    open fun getItem() = ItemRepresentation.asItem(getMaterial())

    override fun setConvertedValue(value: Any?) {
        val convertedValue = getConvertedValue(value)

        return setOptionValue(convertedValue)
    }

    public override fun clone(): Option<*> {
        try {
            return super.clone() as Option<*>
        } catch (e: CloneNotSupportedException) {
            throw AssertionError()
        }
    }
}