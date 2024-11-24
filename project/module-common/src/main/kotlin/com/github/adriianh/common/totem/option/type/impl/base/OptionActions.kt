package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify

class OptionActions : OptionBase<String>() {
    override val id: String = "ACTIONSEXECUTOR"
    override val description: List<String> = listOf("Totem's actions")
    override val optional: Boolean = true

    private var execute: String = "self"

    override fun isTypeCompatible(value: String): Boolean = true

    override fun getDefaultValue(): String = "self"

    override fun getExampleValue(): String = "all"

    override fun setOptionValue(value: String) {
        execute = value
    }

    override fun getOptionValue(): String {
        return execute
    }

    override fun getConvertedValue(value: Any?): String {
        return value as String
    }

    override fun getMaterial(): XMaterial = XMaterial.SPLASH_POTION

    override fun getItemName(): String = "&aActions".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's actions (self, all, none)
            &7Example: &anone
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}