package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify

class OptionRadius : OptionBase<Double>() {
    override val id: String = "RADIUS"
    override val description: List<String> = listOf("Totem's duration")
    override val optional: Boolean = true

    private var radius: Double = 0.0

    override fun isTypeCompatible(value: Double): Boolean = true

    override fun getDefaultValue(): Double = 5.0

    override fun getExampleValue(): Double = 10.5

    override fun setOptionValue(value: Any) {
        radius = getConvertedValue(value)
    }

    override fun getOptionValue(): Double {
        return radius
    }

    override fun getConvertedValue(value: Any?): Double {
        return value as Double
    }

    override fun getMaterial(): XMaterial = XMaterial.TOTEM_OF_UNDYING

    override fun getItemName(): String = "&aRadius".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's radius (in blocks)
            &7Example: &a8.75
            &7Default value: &a${getDefaultValue()}
            
            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}