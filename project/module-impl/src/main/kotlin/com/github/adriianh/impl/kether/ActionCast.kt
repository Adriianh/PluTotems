package com.github.adriianh.impl.kether

import ink.ptms.um.Mythic
import org.bukkit.entity.Player
import taboolib.module.kether.KetherParser
import taboolib.module.kether.combinationParser
import taboolib.module.kether.script

@KetherParser(["cast"], namespace = "plutotems", shared = true)
private fun parser() = combinationParser {
    it.group(symbol(), text()).apply(it) { action, value ->
        when (action) {
            "skill" -> {
                val skill = Mythic.API.getSkillMechanic(value)
                val trigger = Mythic.API.getSkillTrigger("API")

                now {
                    val caster = script().sender?.castSafely<Player>() ?: error("No player found.")
                    skill?.execute(trigger, caster, caster)
                }
            }
            else -> error("Unknown action: $action")
        }
    }
}