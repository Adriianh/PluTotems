package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit

class ActionBossBar : Action<String>() {
    override val id: String = "BOSSBAR"
    override val description: List<String> = listOf("Send a boss bar to the player")

    private var bossbar: String = "Hello World!"

    override fun getExampleValue(): String = "Hello World!"

    override fun convertValue(value: Any?): String {
        return value as String
    }

    override fun setActionValue(value: String) {
        bossbar = value
    }

    override fun getActionValue(): String {
        return bossbar
    }

    override fun getMaterial(): XMaterial = XMaterial.DRAGON_EGG

    override fun getItemName(): String = "&6Command".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Executes a command as the player.
            &7Example: &f/say Hello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        val values = bossbar.split("|", limit = 4)

        val bossBar = Bukkit.createBossBar(
            values[0].colorify(),
            BarColor.valueOf(values[1]),
            BarStyle.valueOf(values[2])
        )

        bossBar.addPlayer(player)

        submit(delay = values[4].toInt() * 20L) {
            bossBar.removePlayer(player)
        }

        return true
    }
}