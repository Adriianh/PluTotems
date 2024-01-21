package com.github.adriianh.core.util

import com.github.adriianh.common.adventure.Adventure.addAudience
import com.github.adriianh.common.config.ConfigLoader.loadFiles
import com.github.adriianh.common.config.ConfigManager
import com.github.adriianh.common.totem.TotemEntityFactory.removeEntities
import com.github.adriianh.common.totem.TotemStructureFactory.undoSessions
import com.github.adriianh.core.PluTotems
import com.github.adriianh.core.util.PluTotemsInfo.getAbout
import com.github.adriianh.core.util.PluTotemsInfo.getGoodbye
import com.github.adriianh.core.util.PluTotemsInfo.getHelp
import com.github.adriianh.core.util.PluTotemsInfo.getLogo
import com.github.adriianh.core.util.PluTotemsTasks.registerTasks
import taboolib.module.lang.Language

object PluTotemsLoader {
    private val plugin = PluTotems.plugin

    fun onPluginLoad() {
        getAbout(plugin)

        Language.path = ConfigManager.settings.getString("Settings.Language")!!
    }

    fun onPluginEnable() {
        getLogo()
        getHelp()
        addAudience(plugin)
        loadFiles()
        registerTasks()
    }

    fun onPluginDisable() {
        getGoodbye()
        undoSessions()
        removeEntities()
    }
}