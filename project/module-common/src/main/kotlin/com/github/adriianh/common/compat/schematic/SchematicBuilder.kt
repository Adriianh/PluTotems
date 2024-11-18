package com.github.adriianh.common.compat.schematic

import com.fastasyncworldedit.core.FaweAPI
import com.github.adriianh.common.compat.OptionParameters
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.math.transform.AffineTransform
import com.sk89q.worldedit.session.ClipboardHolder
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import taboolib.common.platform.function.getDataFolder
import taboolib.platform.BukkitPlugin
import taboolib.platform.util.sendLang
import java.io.File
import java.util.*

object StructureBuilder {
    private val sessions = mutableMapOf<String, EditSession>()

    fun buildSchematic(player: Player, option: OptionParameters) {
        val duration = option.getParameters("DURATION") as? Int ?: 10
        val world = FaweAPI.getWorld(player.world.name)
        val location = player.location

        val schemUUID = "${UUID.randomUUID()}-${player.world.name}"
        val schematic = option.getParameters("SCHEMATICNAME") as? String
            ?: return player.sendLang("Totem-Non-Structure")

        val air = option.getParameters("SCHEMATICAIR") as? Boolean
        val entities = option.getParameters("SCHEMATICENTITIES") as? Boolean
        var rotation = option.getParameters("SCHEMROTATION") as? SchemRotation
        if (rotation == null) {
            rotation = SchemRotation(0.0, 0.0, 0.0)
        }

        val file = File(getDataFolder(), "schematics/$schematic.schem")
        if (!file.exists()) {
            player.sendLang("Totem-Schematic-Not-Found")
            return
        }

        val transform = createTransform(rotation)

        val format = ClipboardFormats.findByFile(file)
        format!!.getReader(file.inputStream()).use { reader ->
            val clipboard = reader.read()

            WorldEdit.getInstance().newEditSession(world).use { session ->
                val holder = ClipboardHolder(clipboard)
                holder.transform = holder.transform.combine(transform)

                val operation: Operation = holder
                    .createPaste(session)
                    .to(BlockVector3.at(location.x, location.y, location.z))
                    .copyEntities(entities!!)
                    .ignoreAirBlocks(air!!)
                    .build()

                sessions[schemUUID] = session
                Operations.complete(operation)

                undoOperationAfterDelay(session, duration).also {
                    sessions.remove(schemUUID)
                }
            }
        }
    }

    private fun createTransform(rotation: SchemRotation): AffineTransform {
        return AffineTransform().also {
            it.rotateX(-rotation.x!!)
            it.rotateY(-rotation.y!!)
            it.rotateZ(-rotation.z!!)
        }
    }

    private fun undoOperationAfterDelay(session: EditSession, delay: Int) {
        object : BukkitRunnable() {
            override fun run() {
                val undoSession = WorldEdit.getInstance().newEditSessionBuilder()
                    .world(session.world)
                    .build()

                session.undo(undoSession)
                session.flushQueue()
                undoSession.flushQueue()
                undoSession.close()

            }
        }.runTaskLaterAsynchronously(BukkitPlugin.getInstance(), delay.toLong() * 20)
    }

    fun getSessions() = sessions
}