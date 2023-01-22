package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.entity.Player
import taboolib.library.xseries.XSound
import taboolib.platform.util.sendLang

object ActionSound : Action {
    override val identifier: String = "SOUND"

    override fun execute(player: Player, value: String) {
        val split = value.split("-")
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