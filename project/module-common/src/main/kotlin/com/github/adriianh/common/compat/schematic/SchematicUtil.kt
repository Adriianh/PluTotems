package com.github.adriianh.common.compat.schematic

import com.sk89q.worldedit.EmptyClipboardException
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operation
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
import org.bukkit.entity.Player
import taboolib.common.platform.function.getDataFolder
import taboolib.platform.util.sendLang
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


data class SchemRotation(
    val x: Double? = null ?: 0.0,
    val y: Double? = null ?: 0.0,
    val z: Double? = null ?: 0.0
)

fun saveSchematic(player: Player, name: String) {
    val actor = BukkitAdapter.adapt(player)
    val manager = WorldEdit.getInstance().sessionManager
    val session = manager.get(actor)

    val folder = File(getDataFolder(), "schematics/")
    if (!folder.exists()) {
        folder.mkdirs()
    }

    if (name.contains("/")) {
        player.sendLang("Command-Schematic-Invalid-Name", name)
        return
    }

    val file = File(getDataFolder(), "schematics/$name.schem")
    if (file.exists()) {
        player.sendLang("Command-Schematic-Already-Exists", name)
        return
    }

    val clipboard: ClipboardHolder = try {
        session!!.clipboard
    } catch (e: NullPointerException) {
        player.sendLang("Command-Schematic-Empty-Clipboard")
        return
    } catch (e: EmptyClipboardException) {
        player.sendLang("Command-Schematic-Empty-Clipboard")
        return
    }

    try {
        BuiltInClipboardFormat.FAST.getWriter(FileOutputStream(file)).use { writer ->
            writer.write(clipboard.clipboards[0])
        }
        player.sendLang("Command-Schematic-Saved", name)
    } catch (e: IOException) {
        e.printStackTrace()
        player.sendLang("Command-Schematic-Saving-Failed", name)
    }
}

fun loadSchematic(
    player: Player,
    name: String
) {
    val world = BukkitAdapter.adapt(player.world)
    val location = player.location

    val file = File(getDataFolder(), "schematics/$name.schem")
    if (!file.exists()) {
        player.sendLang("Totem-Schematic-Not-Found")
        return
    }

    val format = ClipboardFormats.findByFile(file)
    format!!.getReader(file.inputStream()).use { reader ->
        val clipboard = reader.read()

        WorldEdit.getInstance().newEditSession(world).use { session ->
            val holder = ClipboardHolder(clipboard)

            val operation: Operation = holder
                .createPaste(session)
                .to(BlockVector3.at(location.x, location.y, location.z))
                .build()

            Operations.complete(operation)
            player.sendLang("Totem-Schematic-Loaded")
        }
    }
}