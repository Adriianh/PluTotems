import io.izzel.taboolib.gradle.Basic

plugins {
    id("io.izzel.taboolib") version taboolibPluginVersion
}

subprojects {
    /* TabooLib Plugin */
    apply(plugin = "io.izzel.taboolib")

    /* Repositories */
    repositories {
        mavenLocal()
        mavenCentral()
    }

    /* TabooLib Settings */
    taboolib {

        /* Options */
        version {
            taboolib = taboolibVersion
        }

        /* Data */
        env {
            /* Modules */
            install(Basic)
            taboolibModules.forEach {
                install(it)
            }
        }

        /* Plugin Description */
        description {
            name(rootName)

            desc("")

            contributors {
                name("Adriianh")
            }

            links {
                name("discord").url("discord.gg/######")
            }

            /* Information */
            @Suppress("UNCHECKED_CAST")
            arrayOf(
                bukkitNodes as HashMap<String, Any>,
                bungeeNodes as HashMap<String, Any>
            ).forEach {
                /* Build Information */
                it["built-date"] = currentISODate
                it["built-by"] = systemUserName
                it["built-os"] = systemOS
                it["built-ip"] = systemIP
            }
        }
    }

    tasks {
        jar {
            allModules.forEach {
                dependsOn(it.tasks.jar)
            }
        }

        shadowJar {
            dependsOn(taboolibMainTask)
            from(taboolibMainTask.get().inJar)
            dependencies {
                exclude {
                    it.moduleGroup == "io.izzel.taboolib"
                }
            }
            combineFiles.forEach { append(it) }
        }

        build {
            dependsOn(shadowJar)
        }
    }
}

buildDirClean()