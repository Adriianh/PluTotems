package com.github.adriianh.core.util

import taboolib.common.platform.function.info
import taboolib.platform.BukkitPlugin

object PluTotemsInfo {
    private const val GITHUB = "https://github.com/adriian/PluTotems"
    private const val SPIGOTMC = "https://www.spigotmc.org/members/adrian0w0.853456/"
    private const val DISCORD = "https://discord.com/invite/fe6372kRE4"

    fun getAbout(plugin: BukkitPlugin) {
        return """
            Authors: ${plugin.description.authors}
            Version: ${plugin.description.version}
            
            Github: $GITHUB
            SpigotMC: $SPIGOTMC
            Discord: $DISCORD
            
            This plugin is licensed under the MIT License.
        """.trimIndent().lines().forEach { info(it) }
    }

    fun getHelp() {
        return """
            /plutotems - Shows the about page
            /plutotems reload - Reloads the plugin
        """.trimIndent().lines().forEach { info(it) }
    }

    fun getGoodbye() {
        return """
            Thanks for using PluTotems!
            If you have any issues, please report them on the Github page.
            
            Github: $GITHUB
            Discord: $DISCORD
            
            This plugin is licensed under the MIT License.
        """.trimIndent().lines().forEach { info(it) }
    }

    fun getLogo() {
        return """
            ╋╋╋┏┓╋╋┏━━━━┓╋┏┓
            ╋╋╋┃┃╋╋┃┏┓┏┓┃┏┛┗┓
            ┏━━┫┃┏┓┣┫┃┃┣┻┻┓┏╋━━┳┓┏┳━━┓
            ┃┏┓┃┃┃┃┃┃┃┃┃┏┓┃┃┃┃━┫┗┛┃━━┫
            ┃┗┛┃┗┫┗┛┃┃┃┃┗┛┃┗┫┃━┫┃┃┣━━┃
            ┃┏━┻━┻━━┛┗┛┗━━┻━┻━━┻┻┻┻━━┛
            ┃┃
            ┗┛
        """.trimIndent().lines().forEach { info(it) }
    }
}