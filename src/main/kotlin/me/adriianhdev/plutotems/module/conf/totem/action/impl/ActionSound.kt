package me.adriianhdev.plutotems.module.conf.totem.action.impl

import org.bukkit.entity.Player
import taboolib.library.xseries.XSound
import taboolib.platform.util.sendLang

object ActionSound {
    fun execute(player: Player, sound: String) {
        val split = sound.split("-")
        val volume: Float = split.getOrNull(1)?.toFloatOrNull() ?: 1f
        val pitch: Float = split.getOrNull(2)?.toFloatOrNull() ?: 1f

        val sound: XSound
        try {
            sound = XSound.valueOf(split[0])
        } catch (t: Throwable) {
            player.sendLang("Totem-Unknown-Sound")
            return
        }

        sound.play(player, volume, pitch)
    }
}