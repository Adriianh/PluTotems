repositories {
    mavenCentral()

    /* Libraries */
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    /* TabooLib & Bukkit */
    compileTabooLib()
    compileCore(12004)

    /* Bungeecord */
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")

    /* Adventure & MiniMessage */
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")

    /* FastAsyncWorldEdit */
    implementation(platform("com.intellectualsites.bom:bom-newest:1.40"))
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }

    /* NBT API */
    implementation("de.tr7zw:item-nbt-api:2.12.2")
}