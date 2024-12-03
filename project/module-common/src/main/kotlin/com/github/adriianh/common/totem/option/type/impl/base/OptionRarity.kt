package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify

class OptionRarity : OptionBase<String>(OptionTypes.BASE) {
    override val id: String = "RARITY"
    override val description: List<String> = listOf("Totem's rarity")
    override val optional: Boolean = true

    private var rarity: String = "COMMON"

    override fun getOptionPath(): String = "options.$identifier"

    override fun isTypeCompatible(value: String): Boolean = true

    override fun getDefaultValue(): String = "COMMON"

    override fun getExampleValue(): String = "LEGENDARY"

    override fun setOptionValue(value: Any) {
        rarity = getConvertedValue(value)
    }

    override fun getOptionValue(): String {
        return rarity
    }

    override fun getConvertedValue(value: Any?): String {
        return value as String
    }

    override fun getMaterial(): XMaterial = XMaterial.DIAMOND

    override fun getItemName(): String = "&aRarity".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's rarity
            &7Example: &aCOMMON
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}