repositories {
    /* Jitpack */
    maven(url = "https://jitpack.io")

    /* Odalita Menus */
    maven(url = "https://repo.repsy.io/mvn/kiwisap/odalitamenus")
}

dependencies {
    /* Bukkit Core */
    compileCore(12006)

    /* Modules */
    compileModule("module-common")

    /* Bukkit API */
    compileTaboo("platform-bukkit")
    compileTaboo("bukkit-util")
    compileTaboo("minecraft-chat")
    compileTaboo("bukkit-nms", "bukkit-nms-stable", "bukkit-nms-ai")

    /* Odalita Menus */
//    implementation("nl.odalitadevelopments.odalitamenus:core:0.5.10")

    /* Libs Folder */
    implementation(fileTree("libs"))
}

buildDirClean()