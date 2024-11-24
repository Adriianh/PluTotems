package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.util.colorify

class OptionEntityCollidable : OptionEntity<Boolean>() {
    override val id: String = "ENTITYCOLLIDABLE"
    override val description: List<String> = listOf("Enable entity collision")
    override val optional: Boolean = true

    private var collidable: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Boolean) {
        collidable = value
    }

    override fun getOptionValue(): Boolean {
        return collidable
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }
    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aCollidable".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Enable entity collision
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}