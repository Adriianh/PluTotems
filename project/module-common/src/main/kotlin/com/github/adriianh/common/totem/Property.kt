package com.github.adriianh.common.totem

import org.bukkit.entity.Player

interface Property {
    val identifier: String

    fun setConvertedValue(value: Any)

    fun execute(player: Player): Boolean {
        return false
    }
}