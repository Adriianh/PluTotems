package com.github.adriianh.common.totem.condition

import com.github.adriianh.common.totem.ItemRepresentation
import com.github.adriianh.common.totem.Property
import org.bukkit.entity.Player
import taboolib.library.xseries.XMaterial

abstract class Condition<T> : Property, Cloneable {
    abstract val id: String
    abstract val description: List<String>

    override val identifier: String
        get() = id

    abstract fun checkResult(player: Player): Boolean

    abstract fun getExampleValue(): T

    abstract fun getMaterial(): XMaterial

    abstract fun getItemName(): String

    abstract fun getItemLore(): List<String>

    abstract fun convertValue(value: Any?): T

    abstract fun setConditionValue(value: T)

    abstract fun getConditionValue(): T

    fun getItem() = ItemRepresentation.asItem(getMaterial())

    override fun setConvertedValue(value: Any?) {
        val convertedValue = convertValue(value)

        return setConditionValue(convertedValue)
    }

    public override fun clone(): Condition<*> {
        try {
            return super.clone() as Condition<*>
        } catch (e: CloneNotSupportedException) {
            throw AssertionError()
        }
    }
}