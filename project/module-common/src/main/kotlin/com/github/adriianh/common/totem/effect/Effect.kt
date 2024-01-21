package com.github.adriianh.common.totem.effect

import com.github.adriianh.common.totem.ItemRepresentation
import com.github.adriianh.common.util.colorify
import org.bukkit.potion.PotionEffectType
import taboolib.library.xseries.XMaterial
import taboolib.module.configuration.Configuration

class Effect(
    val type: PotionEffectType,
    val duration: Int,
    val amplifier: Int,
    private val description: List<String>? = null
) {
    private fun getMaterial(): XMaterial = XMaterial.POTION

    fun getPotionType(): PotionEffectType = type

    fun getItem() = ItemRepresentation.asItem(getMaterial())

    fun getItemName(): String = "§d${type.name}".colorify()

    fun getItemLore(): List<String> {
        return """
            §7Effect: §d${type.name}
            §7Description: §d$description.            
            
            §7Click to edit.
            
        """.trimIndent().lines().colorify()
    }

    fun setOptionValue(config: Configuration, path: String, value: Any) {
        config["$path.${type.name}"] = value
    }

    fun getOptionValue(config: Configuration, path: String): String {
        return config["$path.${type.name}"].toString()
    }
}