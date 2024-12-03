package com.github.adriianh.core.ui.frame

import com.github.adriianh.common.totem.Totem
import com.github.adriianh.common.totem.option.type.OptionBase
import com.github.adriianh.core.ui.EditorMenu
import com.github.adriianh.core.ui.OptionEditorMenu
import com.github.adriianh.core.ui.usage.AnvilInputMenu
import com.github.adriianh.core.ui.usage.HopperConfirmMenu
import com.github.adriianh.core.util.PluTotemsLoader.getOdalitaMenus
import nl.odalitadevelopments.menus.annotations.MenuFrame
import nl.odalitadevelopments.menus.contents.MenuContents
import nl.odalitadevelopments.menus.items.ClickableItem
import nl.odalitadevelopments.menus.menu.providers.frame.PlayerMenuFrameProvider
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import taboolib.platform.util.ItemBuilder

@MenuFrame(
    height = 2,
    width = 7
)
class SettingsFrame(
    val totem: Totem,
    private val originMenu: EditorMenu
) : PlayerMenuFrameProvider {
    override fun onLoad(player: Player, menuContents: MenuContents) {
        totem.getOptions().forEachIndexed { index, option ->
            menuContents[index] = ClickableItem.of(
                ItemBuilder(option.getItem()).apply {
                    name = option.getItemName()
                    lore += option.getItemLore()
                }.build()
            ) { event: InventoryClickEvent ->
                when (event.click) {
                    ClickType.LEFT -> {
                        if (option.getOptionValue() is Map<*, *>) {
                            getOdalitaMenus()?.openMenu(
                                OptionEditorMenu(totem, option as OptionBase<*>, originMenu), player
                            )
                            return@of
                        }

                        val optionPath = option.getOptionPath().lowercase()

                        getOdalitaMenus()?.openMenu(AnvilInputMenu({ input ->
                            option.setOptionValue(option.getConvertedValue(input))

                            totem.getConfigurationSection().let { path ->
                                path[optionPath] = input
                            }

                            totem.settings?.saveToFile()
                        }, originMenu), player)
                    }

                    ClickType.RIGHT -> {
                        val optionPath = option.getOptionPath().lowercase()

                        getOdalitaMenus()?.openMenu(HopperConfirmMenu({ confirm ->
                            if (confirm) {
                                option.setOptionValue(option.getDefaultValue())

                                totem.getConfigurationSection().let { path ->
                                    path[optionPath] = option.getDefaultValue()
                                }

                                totem.settings?.saveToFile()
                            }
                        }, originMenu), player)

                    }
                    else -> {}
                }
            }
        }
    }
}