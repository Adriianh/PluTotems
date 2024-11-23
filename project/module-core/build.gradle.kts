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
    implementation("io.github.odalita-developments.odalitamenus:core:0.5.13")

    /* Lombok */
    compileOnly("org.projectlombok:lombok:1.18.20")

    /* Libs Folder */
    implementation(fileTree("libs"))

}

buildDirClean()