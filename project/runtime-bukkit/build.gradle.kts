dependencies {
    /* Bukkit Core */
    compileCore(12006)

    /* Modules */
    compileModule("module-core")

    /* Bukkit API */
    compileTaboo("platform-bukkit")
    compileTaboo("bukkit-nms", "bukkit-nms-stable")
}

buildDirClean()