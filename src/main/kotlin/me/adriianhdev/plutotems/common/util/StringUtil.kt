package me.adriianhdev.plutotems.common.util

import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.Totem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.util.Vector

object StringUtil {
    fun String.replaceVar(totem: Totem): String {
        return replace("{id}", totem.id)
            .replace("{name}", totem.name ?: "")
            .replace("{description}", totem.description ?: "")
            .replace("{type}", totem.data.types.type ?: "")
            .replace("{rarity}", totem.rarity ?: "")
            .colorify()
    }

    fun String.toVector(): Vector {
        val split = this.split(",")

        return Vector(
            split[0].toDouble(),
            split[1].toDouble(),
            split[2].toDouble()
        )
    }

    fun String.toMiniMessage(): Component = MiniMessage.miniMessage().deserialize(this)
}