package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.common.util.color.colorify
import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit

object ActionBossbar: Action {
    override val identifier: String = "BOSSBAR"

    override fun execute(player: Player, value: String) {
        val split = value.split(" | ", limit = 4)

        val text = split[0].colorify()
        val color = split[1]
        val style = split[2]
        val time = split.getOrNull(3)?.toIntOrNull() ?: 15

        val bossBar = Bukkit.createBossBar(
            text,
            BarColor.valueOf(color),
            BarStyle.valueOf(style)
        )
        bossBar.addPlayer(player)

        submit(delay = time * 20L) {
            bossBar.removePlayer(player)
        }
    }
}