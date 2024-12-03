package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.totem.option.type.OptionTypes
import com.github.adriianh.common.util.colorify

class OptionHealth : OptionBase<Double>(OptionTypes.BASE) {
    override val id: String = "HEALTH"
    override val description: List<String> = listOf("Totem's health")
    override val optional: Boolean = false

    private var health: Double = 10.0

    override fun getOptionPath(): String = "options.$identifier"

    override fun isTypeCompatible(value: Double): Boolean = true

    override fun getDefaultValue(): Double = 10.0

    override fun getExampleValue(): Double = 20.0

    override fun setOptionValue(value: Any) {
        health = getConvertedValue(value)
    }

    override fun getOptionValue(): Double {
        return health
    }

    override fun getConvertedValue(value: Any?): Double {
        return value as Double
    }

    override fun getMaterial(): XMaterial = XMaterial.GOLDEN_APPLE

    override fun getItemName(): String = "&aHealth".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit health that totem will give
            &7Example: &a10.0
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}