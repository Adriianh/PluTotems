package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify

class OptionEntityKill : OptionEntity<Boolean>(OptionTypes.ENTITY) {
    override val id: String = "KILL"
    override val description: List<String> = listOf("Run totem when entity is killed")
    override val optional: Boolean = true

    private var execute: Boolean = false

    override fun getOptionPath(): String = "options.entity.$identifier"

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = false

    override fun getExampleValue(): Boolean = true

    override fun setOptionValue(value: Any) {
        execute = getConvertedValue(value)
    }

    override fun getOptionValue(): Boolean {
        return execute
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return when (value) {
            is Boolean -> value
            is String -> value.toBoolean()
            else -> throw IllegalArgumentException("Value is not a boolean")
        }
    }

    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aEffects".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Run totem when entity is killed (true, false)
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}