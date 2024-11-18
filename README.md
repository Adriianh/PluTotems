# PluTotems
**This plugin is still in development, so it may not work as expected.**
**It's my first plugin, you probably think the code is bad, sorry, I'll try to improve it.**

PluTotems is a plugin that allows you to create totems with custom effects, triggers and conditions.

## Features
- Create totems with custom actions, effects, triggers and conditions.
- Execute actions when a player interacts with the totem.
- Heads can be used as totems.
- CustomModelData support (1.14+, also in the totem effect!).

## Commands
`[arg]` = optional, `<arg>` = required

- `/totem` - Main command.
- `/totem list` - List all totems.
- `/totem reload` - Reload the plugin.
- `/totem help` - Show the help menu.
- `/totem create <name>` - Create a totem. (WIP)
- `/totem delete <name>` - Remove a totem. (WIP)
- `/totem info <name>` - Show info about a totem.
- `/totem give <name> <player>` - Give a totem to a player.
- `/totem get <name>` - Get a totem.
- `/totem remove <name> [player]` - Remove a totem from a player.

## TODO
- [ ] Add more actions
- [ ] Add more triggers
- [X] Add totem types (Item, Armor, [Structure?])

# Building
* [Gradle](https://gradle.org/) - Dependency Management

GradleWrapper is included in this project.

**Windows:**

```
gradlew.bat jar
```

**macOS/Linux:**

```
./gradlew jar
```

Build artifacts should be found in `./build/libs` folder.
