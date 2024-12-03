package com.github.adriianh.core.ui

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.option.Option
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.core.ui.MenuHelper.FILL_ITEM
import com.github.adriianh.core.ui.frame.OptionFrame
import nl.odalitadevelopments.menus.annotations.Menu
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.DisplayItem
import nl.odalitadevelopments.menus.menu.providers.PlayerMenuProvider
import nl.odalitadevelopments.menus.menu.type.MenuType
import org.bukkit.entity.Player
import taboolib.platform.util.ItemBuilder

@Menu(
    title = "Editing | Option Menu",
    type = MenuType.CHEST_6_ROW
)
class OptionEditorMenu(
    val totem: Totem,
    private val option: OptionBase<*>,
    private val originMenu: EditorMenu
) : PlayerMenuProvider {
    override fun onLoad(player: Player, menuContents: MenuContents) {
        menuContents.fill(FILL_ITEM)
        menuContents[13] = DisplayItem.of(
            ItemBuilder(option.getItem()).apply {
                name = option.getItemName()
            }.build()
        )

        menuContents.registerFrame("options-icons", 28, OptionFrame::class.java)
        menuContents.loadFrame("options-icons", totem, option)
    }
}