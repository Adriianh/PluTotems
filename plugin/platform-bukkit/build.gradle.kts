dependencies {
    /* Core - Common Modules */
    shadowModule("module-core")
    shadowModule("module-common")

    /* Runtime Bukkit */
    shadowModule("runtime-bukkit")
}

taboolib {
    env {
        /* Bukkit Platform */
        install("platform-bukkit", "platform-bukkit-impl")
        /* Bukkit Utilities */
        install("bukkit-hook", "bukkit-util")
        /* Kether */
        install("minecraft-kether")
    }

    description {
        dependencies {
            name("FastAsyncWorldEdit").with("bukkit").optional(true)
        }
    }
}

tasks.shadowJar {
    archiveFileName.set("$rootName-Bukkit-$rootVersion.jar")
}

buildDirClean()