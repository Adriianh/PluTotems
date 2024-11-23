package com.github.adriianh.core.command.impl

import com.github.adriianh.common.totem.TotemFactory
import com.github.adriianh.common.totem.TotemRegistry
import com.github.adriianh.common.totem.action.ActionRegistry
import com.github.adriianh.common.totem.condition.ConditionRegistry
import com.github.adriianh.common.totem.option.OptionRegistry
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.isAir

@CommandHeader(
    name = "info",
    aliases = ["i"],
    permission = "plutotems.command.info",
    description = "Get information about a totem."
)
object InfoCommand {
    @CommandBody
    val listOptions = subCommand {
        dynamic(comment = "option") {
            suggestion<Player> { _, _ ->
                OptionRegistry.getOptions().map { it.id }
            }
            execute<CommandSender> { sender, context, _ ->
                sender.sendMessage("${OptionRegistry.getOption(context["option"])?.getExampleValue()}")
            }
        }
    }

    @CommandBody
    val listActions = subCommand {
        dynamic(comment = "action") {
            suggestion<Player> { _, _ ->
                ActionRegistry.getActions().map { it.id }
            }
            execute<CommandSender> { sender, context, _ ->
                sender.sendMessage("${ActionRegistry.getAction(context["action"])?.getExampleValue()}")
            }
        }
    }

    @CommandBody
    val listConditions = subCommand {
        dynamic(comment = "condition") {
            suggestion<Player> { _, _ ->
                ConditionRegistry.getConditions().map { it.id }
            }
            execute<CommandSender> { sender, context, _ ->
                sender.sendMessage("${ConditionRegistry.getCondition(context["condition"])?.getExampleValue()}")
            }
        }
    }

    @CommandBody
    val getOption = subCommand {
        dynamic(comment = "id") {
            suggestion<Player> { _, _ ->
                TotemRegistry.getTotems().map { it.id }
            }
            dynamic(comment = "option") {
                suggestion<Player> { _, context ->
                    val totem = TotemRegistry.getTotem(context["id"])

                    totem?.getOptions()?.map { it.id }
                }
                execute<CommandSender> { sender, context, _ ->
                    val totem = TotemRegistry.getTotem(context["id"])!!
                    val option = totem.getOption(context["option"])

                    sender.sendMessage("${option?.getOptionValue()}")
                }
            }
        }
    }

    @CommandBody
    val getActions = subCommand {
        dynamic(comment = "id") {
            suggestion<Player> { _, _ ->
                TotemRegistry.getTotems().map { it.id }
            }
            dynamic(comment = "action") {
                suggestion<Player> { _, context ->
                    val totem = TotemRegistry.getTotem(context["id"])

                    totem?.getActions()?.map { it.id }
                }
                execute<CommandSender> { sender, context, _ ->
                    val totem = TotemRegistry.getTotem(context["id"])!!
                    val action = totem.getAction(context["action"])

                    sender.sendMessage("${action?.getActionValue()}")
                }
            }
        }
    }

    @CommandBody
    val getConditions = subCommand {
        dynamic(comment = "id") {
            suggestion<Player> { _, _ ->
                TotemRegistry.getTotems().map { it.id }
            }
            dynamic(comment = "condition") {
                suggestion<Player> { _, context ->
                    val totem = TotemRegistry.getTotem(context["id"])

                    totem?.getConditions()?.map { it.id }
                }
                execute<CommandSender> { sender, context, _ ->
                    val totem = TotemRegistry.getTotem(context["id"])!!
                    val condition = totem.getCondition(context["condition"])

                    sender.sendMessage("${condition?.getConditionValue()}")
                }
            }
        }
    }

    @CommandBody
    val getEffects = subCommand {
        dynamic(comment = "id") {
            suggestion<Player> { _, _ ->
                TotemRegistry.getTotems().map { it.id }
            }
            dynamic(comment = "effect") {
                suggestion<Player> { _, context ->
                    val totem = TotemRegistry.getTotem(context["id"])

                    totem?.getEffects()?.map { it.type.name }
                }
                execute<CommandSender> { sender, context, _ ->
                    val totem = TotemRegistry.getTotem(context["id"])!!
                    val effect = totem.getEffect(context["effect"])

                    sender.sendMessage("${effect?.duration} - ${effect?.amplifier}")
                }
            }
        }
    }

    @CommandBody
    val checkItem = subCommand {
        execute<Player> { player, _, _ ->
            val item = player.inventory.itemInMainHand

            if (item.isAir) {
                player.sendMessage("You must be holding an item.")
                return@execute
            }

            if (TotemFactory.isTotem(item)) {
                val totem = TotemFactory.getTotem(item)
                player.sendMessage("Totem: ${totem.id}")
            } else {
                player.sendMessage("This item is not a totem.")
            }
        }
    }
}