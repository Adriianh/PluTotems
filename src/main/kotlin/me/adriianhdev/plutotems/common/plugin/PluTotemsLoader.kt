package me.adriianhdev.plutotems.common.plugin

import me.adriianhdev.plutotems.PluTotems
import me.adriianhdev.plutotems.module.conf.ConfigLoader
import me.adriianhdev.plutotems.module.conf.ConfigManager
import me.adriianhdev.plutotems.module.internal.task.EffectTask
import org.bukkit.Bukkit
import taboolib.common.platform.function.info
import taboolib.module.lang.Language

object PluTotemsLoader {
    fun load() {
        Language.default = "en_US"
        info("Loading PluTotems...")
    }

    fun init() {
        logoText()
        aboutText()
        ConfigLoader.loader()
        registerTasks()
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