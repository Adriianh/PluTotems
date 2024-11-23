package com.github.adriianh.core.util

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
import nl.odalitadevelopments.menus.OdalitaMenus
import taboolib.module.lang.Language

object PluTotemsLoader {
    private val plugin = PluTotems.plugin
    private var odalitaMenus = PluTotems.odalitaMenus

    fun onPluginLoad() {
        getAbout(plugin)

        Language.path = ConfigManager.settings.getString("Settings.Language").toString()
    }

    fun onPluginEnable() {
        odalitaMenus = OdalitaMenus.createInstance(plugin)

        getLogo()
        getHelp()
        loadFiles()
        registerTasks()
    }

    fun onPluginDisable() {
        getGoodbye()
        undoSessions()
        removeEntities()
    }

    fun getOdalitaMenus(): OdalitaMenus? {
        return odalitaMenus
    }
}