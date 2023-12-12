package me.adriianhdev.plutotems.plugin

import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.configuration.ConfigLoader
import me.adriianhdev.plutotems.configuration.ConfigManager
import me.adriianhdev.plutotems.core.task.EffectTask
import me.adriianhdev.plutotems.util.EntityUtil
import me.adriianhdev.plutotems.util.SchematicUtil
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import org.bukkit.Bukkit
import taboolib.common.platform.function.info
import taboolib.module.lang.Language


object PluTotemsLoader {
    private var adventure: BukkitAudiences? = null

    fun adventure(): BukkitAudiences {
        checkNotNull(adventure) { "Tried to access Adventure when the plugin was disabled!" }
        return adventure!!
    }

    fun load() {
        Language.default = "en_US"
        info("Language set to: ${Language.default}")
    }

    fun init() {
        logoText()
        aboutText()
        ConfigLoader.loader()
        registerTasks()
        this.adventure = BukkitAudiences.create(PluTotems.plugin)
    }

    fun unload() {
        info("Unloading PluTotems...")
        SchematicUtil.undoAll()
        EntityUtil.removeEntity()

        if (this.adventure != null) {
            this.adventure!!.close()
            this.adventure = null
        }
    }

    fun reload() {
        info("Reloading PluTotems...")
        ConfigManager.reload()
        ConfigLoader.loader()
    }

    private fun registerTasks() {
        Bukkit.getServer().scheduler.runTaskTimer(PluTotems.plugin, EffectTask, 0L, 20L)
    }

    private fun aboutText() {
        val about = listOf(
            "Authors: ${PluTotems.plugin.description.authors}",
            "Version: ${PluTotems.plugin.description.version}",
            "",
            "Github: Adriianh",
            "SpigotMC: Adrian0w0",
            "Discord: Adriiánh#1754"
        )

        about.forEach { info(it) }
    }

    private fun logoText() {
        val logo = listOf(
            "╋╋╋┏┓╋╋┏━━━━┓╋┏┓",
            "╋╋╋┃┃╋╋┃┏┓┏┓┃┏┛┗┓",
            "┏━━┫┃┏┓┣┫┃┃┣┻┻┓┏╋━━┳┓┏┳━━┓",
            "┃┏┓┃┃┃┃┃┃┃┃┃┏┓┃┃┃┃━┫┗┛┃━━┫",
            "┃┗┛┃┗┫┗┛┃┃┃┃┗┛┃┗┫┃━┫┃┃┣━━┃",
            "┃┏━┻━┻━━┛┗┛┗━━┻━┻━━┻┻┻┻━━┛",
            "┃┃",
            "┗┛"
        )

        logo.forEach { info(it) }
    }
}