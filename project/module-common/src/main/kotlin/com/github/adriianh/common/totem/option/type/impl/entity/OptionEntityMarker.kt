package com.github.adriianh.common.totem.option.type.impl.entity

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.util.colorify

class OptionEntityMarker : OptionEntity<Boolean>() {
    override val id: String = "ENTITYMARKER"
    override val description: List<String> = listOf("If the entity is a marker")
    override val optional: Boolean = true

    private var isMarker: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Any) {
        isMarker = getConvertedValue(value)
    }

    override fun getOptionValue(): Boolean {
        return isMarker
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }
    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aMarker".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7if the entity is a marker
            &7Example: &atrue
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}