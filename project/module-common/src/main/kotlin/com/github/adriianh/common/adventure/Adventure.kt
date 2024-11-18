package com.github.adriianh.common.adventure

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.platform.BukkitPlugin

object Adventure {
    val audiences by lazy {
        BukkitAudiences.create(BukkitPlugin.getInstance())
    }

    val Player.adventurePlayer
        get() = audiences.player(this)

    val CommandSender.adventureSender
        get() = audiences.sender(this)
}