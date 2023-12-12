package me.adriianhdev.plutotems.core.totem

import org.bukkit.inventory.ItemStack

data class TotemScript(
    val itemStack: ItemStack,
    val conditions: Condition,
    val options: Options,
    val types: Types,
    val actions: List<String>? = null,
    val scripts: List<String>? = null,
    val effects: List<String>? = null,
    val heldEffect: List<String>? = null
)