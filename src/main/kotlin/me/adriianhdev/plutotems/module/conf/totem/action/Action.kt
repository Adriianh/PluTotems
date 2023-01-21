package me.adriianhdev.plutotems.module.conf.totem.action

import org.bukkit.entity.Player

interface Action {
    val identifier: String
    fun execute(player: Player, value: String)
}