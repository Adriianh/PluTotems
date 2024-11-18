package com.github.adriianh.core

import nl.odalitadevelopments.menus.OdalitaMenus
import taboolib.platform.BukkitPlugin

object PluTotems {
    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    var odalitaMenus: OdalitaMenus? = null
}