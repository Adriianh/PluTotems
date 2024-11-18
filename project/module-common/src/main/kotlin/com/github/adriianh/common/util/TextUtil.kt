package com.github.adriianh.common.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

object TextUtil {
    fun String.toMiniMessage(): Component {
        return MiniMessage.miniMessage().deserialize(this)
    }

    fun List<String>.toMiniMessage(): Component {
        return this.map { it.toMiniMessage() }.reduce { acc, component ->
            acc.append(component)
        }
    }
}