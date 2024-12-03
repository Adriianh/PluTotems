package com.github.adriianh.core.ui.frame

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.option.Option
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.common.util.colorify
import nl.odalitadevelopments.menus.annotations.MenuFrame
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.ClickableItem
import nl.odalitadevelopments.menus.menu.providers.frame.PlayerMenuFrameProvider
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import taboolib.library.xseries.XMaterial
import taboolib.platform.util.ItemBuilder

@MenuFrame(
    height = 2,
    width = 7
)
class OptionFrame(
    private val totem: Totem,
    private val option: OptionBase<*>
) : PlayerMenuFrameProvider {
    override fun onLoad(player: Player, menuContents: MenuContents) {
        val optionMap = option.getOptionValue() as Map<*, *>

        optionMap.forEach { (key, value) ->
            menuContents.add(
                ClickableItem.of(
                    ItemBuilder(XMaterial.PAPER).apply {
                        name = "&a${key.toString()}".colorify()
                    }.build()
                ) { event: InventoryClickEvent ->

                }
            )
        }
    }
}