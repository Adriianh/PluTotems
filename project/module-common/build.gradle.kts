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
    compileTaboo("bukkit-xseries")
    compileTaboo("bukkit-xseries")
    compileTaboo("minecraft-chat")
    compileTaboo("minecraft-kether")
    compileTaboo("basic-configuration")

    /* BungeeCord */
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")

    /* Adventure & MiniMessage */
    adventure()

    /* FastAsyncWorldEdit */
    implementation(platform("com.intellectualsites.bom:bom-newest:1.40"))
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }

    /* PersistentDataType */
    implementation("com.jeff-media:MorePersistentDataTypes:2.4.0")
}

buildDirClean()