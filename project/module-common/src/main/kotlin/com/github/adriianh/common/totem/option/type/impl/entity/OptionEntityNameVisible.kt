package com.github.adriianh.common.totem.option.type.impl.entity

import com.github.adriianh.common.totem.option.type.OptionEntity
import com.github.adriianh.common.util.colorify
import taboolib.library.xseries.XMaterial

class OptionEntityNameVisible : OptionEntity<Boolean>() {
    override val id: String = "ENTITYNAMEVISIBLE"
    override val description: List<String> = listOf("Show entity name above head")
    override val optional: Boolean = true

    private var visible: Boolean = true

    override fun isTypeCompatible(value: Boolean): Boolean = true

    override fun getDefaultValue(): Boolean = true

    override fun getExampleValue(): Boolean = false

    override fun setOptionValue(value: Boolean) {
        visible = value
    }

    override fun getOptionValue(): Boolean {
        return visible
    }

    override fun getConvertedValue(value: Any?): Boolean {
        return value as Boolean
    }

    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aName Visible".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Show entity name above head
            &7Example: &afalse
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}