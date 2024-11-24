package com.github.adriianh.common.totem.option.type.impl.base

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.TimeUtil
import com.github.adriianh.common.util.colorify

class OptionCooldown : OptionBase<Long>() {
    override val id: String = "COOLDOWN"
    override val description: List<String> = listOf("Totem's cooldown")
    override val optional: Boolean = true

    private var cooldown: Long = 0L

    override fun isTypeCompatible(value: Long): Boolean = true

    override fun getDefaultValue(): Long = 0L

    override fun getExampleValue(): Long = 15L

    override fun setOptionValue(value: Long) {
        cooldown = value
    }

    override fun getOptionValue(): Long {
        return cooldown
    }

    override fun getConvertedValue(value: Any?): Long {
        return when (value) {
            is String -> TimeUtil.parseTimeToMillis(value) / 1000
            is Long -> value
            is Int -> value.toLong()
            else -> throw IllegalArgumentException("Invalid value type for cooldown")
        }
    }

    override fun getMaterial(): XMaterial = XMaterial.EXPERIENCE_BOTTLE

    override fun getItemName(): String = "&aCooldown".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Edit totem's cooldown
            &7Example: &a10 or 1h30m
            &7Default value: &a${getDefaultValue()}

            &7» &6Left click to edit
            &7» &6Right click to reset
            &7
        """.trimIndent().lines().colorify()
    }
}