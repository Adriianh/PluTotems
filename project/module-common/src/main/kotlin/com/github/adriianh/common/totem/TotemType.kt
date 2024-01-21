package com.github.adriianh.common.totem

import java.util.*

enum class TotemType(name: String) {
    ITEM(name = "ITEM"),
    ARMOR(name = "ARMOR"),
    STRUCTURE(name = "STRUCTURE"),
    ENTITY(name = "ENTITY");
}

fun getTypeByName(name: String): TotemType {
    return TotemType.valueOf(name.uppercase(Locale.getDefault()))
}