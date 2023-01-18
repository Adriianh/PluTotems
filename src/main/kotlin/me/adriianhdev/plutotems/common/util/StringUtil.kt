package me.adriianhdev.plutotems.common.util

import me.adriianhdev.plutotems.module.conf.totem.Totem

object StringUtil {
    fun String.replaceVar(totem: Totem): String {
        return replace("{id}", totem.id)
            .replace("{name}", totem.name ?: "")
            .replace("{description}", totem.description ?: "")
            .replace("{type}", totem.type ?: "")
            .replace("{rarity}", totem.rarity ?: "")
    }
}