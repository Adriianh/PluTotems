package com.github.adriianh.common.totem.action.impl

import com.cryptomorin.xseries.XMaterial
import com.cryptomorin.xseries.XSound
import com.github.adriianh.common.totem.action.Action
import com.github.adriianh.common.util.colorify
import org.bukkit.entity.Player
import taboolib.platform.util.sendLang

class ActionSound : Action<String>() {
    override val id: String = "SOUND"
    override val description: List<String> = listOf("Plays a sound to the player")

    private var sound: String = "ENTITY_PLAYER_LEVELUP-1-1"

    override fun getExampleValue(): String = "ENTITY_VILLAGER_YES-1-1"

    override fun convertValue(value: Any?): String {
        return value as String
    }

    override fun setActionValue(value: String) {
        sound = value
    }

    override fun getActionValue(): String {
        return sound
    }

    override fun getMaterial(): XMaterial = XMaterial.PAPER

    override fun getItemName(): String = "&6Sound".colorify()

    override fun getItemLore(): List<String> {
        return """
            &7Plays a sound to the player.
            &7Example: &fHello world!
            &7
            &7» &6Left click to edit
            &7» &6Right click to delete
            &7
        """.trimIndent().lines().colorify()
    }

    override fun execute(player: Player): Boolean {
        val values = sound.split("-")
        val sound: XSound

        try {
            sound = XSound.valueOf(values[0])
        } catch (e: IllegalArgumentException) {
            player.sendLang("Totem-Sound-Not-Found")
            return false
        }

        sound.play(player, values[1].toFloat(), values[2].toFloat())
        return true
    }
}