package com.github.adriianh.common.totem

import com.fastasyncworldedit.core.FaweAPI
import com.github.adriianh.common.compat.schematic.StructureBuilder.buildSchematic
import com.github.adriianh.common.compat.schematic.StructureBuilder.getSessions
import com.sk89q.worldedit.WorldEdit
import org.bukkit.entity.Player

object TotemStructureFactory {
    fun spawnSchematic(player: Player, totem: Totem) {
        buildSchematic(player, totem)
    }

    fun undoSessions() {
        val sessions = getSessions()
        if (sessions.isEmpty()) return

        try {
            sessions.forEach { (uuid, session) ->
                val world = uuid.split("-")[1]
                val undoSession = WorldEdit.getInstance().newEditSessionBuilder()
                    .world(FaweAPI.getWorld(world))
                    .build()

                session.undo(undoSession)
                session.flushQueue()
                undoSession.flushQueue()
                undoSession.close()
            }

            sessions.clear()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}