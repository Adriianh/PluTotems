package me.adriianhdev.plutotems.module.conf.totem.action.impl

import me.adriianhdev.plutotems.module.conf.totem.action.Action
import org.bukkit.Material
import org.bukkit.entity.Player
import taboolib.library.xseries.parseToItemStack
import xyz.xenondevs.particle.ParticleBuilder
import xyz.xenondevs.particle.ParticleEffect
import xyz.xenondevs.particle.data.color.NoteColor
import xyz.xenondevs.particle.data.texture.BlockTexture
import xyz.xenondevs.particle.data.texture.ItemTexture
import java.awt.Color

object ActionParticle: Action {
    override val identifier: String = "PARTICLE"

    override fun execute(player: Player, value: String) {
        val split = value.split(" | ")
        val particle = split[0]
        val amount = split[1].toInt()
        val offsetX = split[2].toFloat()
        val offsetY = split[3].toFloat()
        val offsetZ = split[4].toFloat()
        val speed = split[5].toFloat()

        val effect = ParticleBuilder(ParticleEffect.valueOf(particle), player.location)
            .setAmount(amount)
            .setOffset(offsetX, offsetY, offsetZ)
            .setSpeed(speed)

        if (split.size > 6) {
            val arg = split[6]

            if (particle == "REDSTONE") {
                effect.setColor(Color.decode(arg))
            }

            if (particle == "NOTE") {
                effect.particleData = NoteColor(arg.toInt())
            }

            if (particle == "ITEM_CRACK") {
                effect.particleData = ItemTexture(arg.parseToItemStack())
            }

            if (particle == "FALLING_DUST") {
                effect.particleData = BlockTexture(Material.valueOf(arg))
            }
        }

        effect.display()
    }
}