package com.github.adriianh.core.ui

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.core.ui.MenuHelper.FILL_ITEM
import com.github.adriianh.core.ui.frame.SettingsFrame
import nl.odalitadevelopments.menus.annotations.Menu
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.ClickableItem
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider
import nl.odalitadevelopments.menus.menu.type.MenuType
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

@Menu(
    title = "Totem Editor | Menu",
    type = MenuType.CHEST_6_ROW
)
class EditorMenu(val totem: Totem) : PlayerMenuProvider {
    override fun onLoad(player: Player, menuContents: MenuContents) {
        menuContents.fill(FILL_ITEM)
        menuContents[13] = ClickableItem.of(totem.item) { event: InventoryClickEvent -> TODO() }

        menuContents.registerFrame("options-icons", 28, SettingsFrame::class.java)
        menuContents.loadFrame("options-icons", totem, this)
    }
}