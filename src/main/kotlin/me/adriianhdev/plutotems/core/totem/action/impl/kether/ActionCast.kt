package me.adriianhdev.plutotems.core.totem.action.impl.kether

import ink.ptms.um.Mythic
import ink.ptms.um.Skill
import me.adriianhdev.plutotems.util.KetherUtil.getBukkitPlayer
import taboolib.common.platform.function.submit
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture

class ActionCast {
    class MythicMobsCast(private val mechanic: Skill, private val trigger: Skill.Trigger) : ScriptAction<Void>() {
        override fun run(frame: ScriptFrame): CompletableFuture<Void> {
            val player = frame.getBukkitPlayer()
            submit { mechanic.execute(trigger, player, player, emptySet(), emptySet(), 0f, emptyMap()) }
            return CompletableFuture.completedFuture(null)
        }
    }

    companion object {
        @KetherParser(["mythicmobs", "mm"], shared = true)
        fun parser() = scriptParser {
            when (it.expects("cast")) {
                "cast" -> {
                    val skill = it.nextToken()
                    val mechanic = Mythic.API.getSkillMechanic(skill) ?: error("unknown skill $skill")
                    val trigger = try {
                        it.mark()
                        it.expects("with", "as", "by")
                        Mythic.API.getSkillTrigger(it.nextToken())
                    } catch (ex: Throwable) {
                        it.reset()
                        Mythic.API.getSkillTrigger("DEFAULT")
                    }
                    MythicMobsCast(mechanic, trigger)
                }

                else -> error("out of case")
            }
        }
    }
}