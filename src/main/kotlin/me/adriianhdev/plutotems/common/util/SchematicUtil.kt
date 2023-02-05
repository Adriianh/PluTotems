package me.adriianhdev.plutotems.common.util

import com.fastasyncworldedit.core.FaweAPI
import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.EmptyClipboardException
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.math.transform.AffineTransform
import com.sk89q.worldedit.session.ClipboardHolder
import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.module.conf.totem.Totem
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.info
import taboolib.platform.util.sendLang
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

data class TotemSchematic(
    val name: String? = null,
    val rotationX: Double? = null,
    val rotationY: Double? = null,
    val rotationZ: Double? = null,
    val entities: Boolean = true,
    val air: Boolean = true
)

object SchematicUtil {
    private var schematics: HashMap<String, EditSession> = HashMap()

    fun paste(player: Player, totem: Totem) {
        if (!PluTotems.plugin.server.pluginManager.isPluginEnabled("FastAsyncWorldEdit")) {
            error("FastAsyncWorldEdit is not enabled, this function won't work.")
        }

        val duration = totem.data.types.duration
        val schematic = totem.data.types.schematic?.name ?: return player.sendLang("Totem-Unknown-Schematic")
        val location = player.location
        val world = FaweAPI.getWorld(location.world!!.name)

        val rotationX = totem.data.types.schematic.rotationX ?: 0.0
        val rotationY = totem.data.types.schematic.rotationY ?: 0.0
        val rotationZ = totem.data.types.schematic.rotationZ ?: 0.0
        val copyEntities = totem.data.types.schematic.entities
        val ignoreAirBlocks = totem.data.types.schematic.air

        val file = File(getDataFolder(), "schematics/$schematic.schem")
        if (!file.exists()) {
            player.sendLang("Command-Schematic-Not-Found", schematic)
            return
        }

        val transform = AffineTransform().also {
            it.rotateX(-rotationX)
            it.rotateY(-rotationY)
            it.rotateZ(-rotationZ)
        }

        val format = ClipboardFormats.findByFile(file)
        format!!.getReader(file.inputStream()).use { reader ->
            val clipboard = reader.read()
            val uuid = "${UUID.randomUUID()}:${world.name}"

            WorldEdit.getInstance().editSessionFactory.getEditSession(world, -1).use { session ->
                val holder = ClipboardHolder(clipboard)
                holder.transform = holder.transform.combine(transform)

                val operation: Operation = holder
                    .createPaste(session)
                    .to(BlockVector3.at(location.x, location.y, location.z))
                    .copyEntities(copyEntities)
                    .ignoreAirBlocks(ignoreAirBlocks)
                    .build()

                Operations.complete(operation)
                schematics[uuid] = session

                val undoRunnable: BukkitRunnable = object : BukkitRunnable() {
                    override fun run() {
                        val undo: EditSession? = WorldEdit.getInstance()
                            .editSessionFactory
                            .getEditSession(world, -1)

                        session.undo(undo)
                        session.flushQueue()
                        undo!!.flushQueue()

                        schematics.remove(uuid)
                    }
                }
                undoRunnable.runTaskLater(PluTotems.plugin, duration * 20L)
            }
        }
    }

    fun save(player: Player, name: String) {
        val actor: com.sk89q.worldedit.entity.Player = BukkitAdapter.adapt(player)
        val manager = WorldEdit.getInstance().sessionManager
        val localSession = manager[actor]

        val folder = File(getDataFolder(), "schematics/")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        if (name.contains("/")) {
            player.sendLang("Command-Schematic-Invalid-Name")
            return
        }

        val file = File(getDataFolder(), "schematics/$name.schem")
        if (file.exists()) {
            player.sendLang("Command-Schematic-Already-Exists", name)
            return
        }

        val clipboard: ClipboardHolder = try {
            localSession.clipboard
        } catch (e: EmptyClipboardException) {
            player.sendLang("Command-Schematic-Empty-Clipboard")
            return
        }

        try {
            BuiltInClipboardFormat.FAST.getWriter(FileOutputStream(file))
                .use { writer -> writer.write(clipboard.clipboard) }
            player.sendLang("Command-Schematic-Saved", name)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun undoAll() {
        info("Undoing all pasted schematics...")
        info("Schematics: ${schematics.size}")

        try {
            schematics.forEach { (uuid, session) ->
                val world = uuid.split(":")[1]
                val undo: EditSession? = WorldEdit.getInstance()
                    .editSessionFactory
                    .getEditSession(FaweAPI.getWorld(world), -1)

                session.undo(undo)
                session.flushQueue()
                undo!!.flushQueue()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            error("An error occurred while trying to undo the pasted schematics")
        }

        schematics.clear()
    }
}