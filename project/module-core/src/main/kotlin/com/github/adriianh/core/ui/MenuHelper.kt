package com.github.adriianh.core.ui

import com.github.adriianh.core.util.PluTotemsLoader.getOdalitaMenus
import org.bukkit.entity.Player

object MenuHelper {
    fun openListMenu(player: Player) {
        getOdalitaMenus()?.openMenu(ListMenu(), player)
    }
}