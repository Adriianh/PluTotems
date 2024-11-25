repositories {
    mavenCentral()

    /* Libraries */
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    maven(url = "https://repo.codemc.io/repository/maven-public/")
    maven(url = "https://maven.enginehub.org/repo/")
}

dependencies {
    /* Bukkit Core */
    compileCore(12006)

    /* Bukkit API */
    compileTaboo("platform-bukkit")
    compileTaboo("bukkit-util")
    compileTaboo("minecraft-kether")

    /* MythicMobs */
    compileOnly("ink.ptms:um:1.1.2")
}
buildDirClean()