package com.github.adriianh.common.totem.action

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.ItemRepresentation
import com.github.adriianh.common.totem.Property

abstract class Action<T> : Property, Cloneable {
    abstract val id: String
    abstract val description: List<String>

    override val identifier: String
        get() = id

    abstract fun getExampleValue(): Any

    abstract fun getConvertedValue(value: Any?): Any

    abstract fun setActionValue(value: Any)

    abstract fun getActionValue(): T

    abstract fun getMaterial(): XMaterial

    abstract fun getItemName(): String

    abstract fun getItemLore(): List<String>

    fun getItem() = ItemRepresentation.asItem(getMaterial())

     override fun setConvertedValue(value: Any) {
        return setActionValue(getConvertedValue(value))
    }

    public override fun clone(): Action<*> {
        try {
            return super.clone() as Action<*>
        } catch (e: CloneNotSupportedException) {
            throw AssertionError()
        }
    }
}