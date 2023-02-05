plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.55"
    id("org.jetbrains.kotlin.jvm") version "1.8.0"
}

taboolib {
    install("common")
    install("common-5")
    install("module-chat")
    install("module-configuration")
    install("module-kether")
    install("module-lang")
    install("module-nms", "module-nms-util")
    install("module-navigation", "module-ai")
    install("platform-bukkit")
    classifier = null
    version = "6.0.10-86"

    description {
        contributors {
            name("Adriianh")
            desc("Create your own totems with custom properties")
        }
        prefix("PluTotems")
        dependencies {
            name("FastAsyncWorldEdit").optional(true)
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.tabooproject.org/repository/releases")
    maven("https://eldonexus.de/repository/maven-public")
}

dependencies {
    implementation("xyz.xenondevs:particle:1.8.3")
    implementation("de.eldoria:eldo-util:1.14.1")
    implementation("net.kyori:adventure-api:4.12.0")
    implementation("net.kyori:adventure-platform-bukkit:4.2.0")
    implementation("net.kyori:adventure-text-minimessage:4.12.0")

    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.5.1") { isTransitive = false }
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.5.1")

    taboo("ink.ptms:um:1.0.0-beta-23")
    compileOnly("ink.ptms.core:v11903:11903-minimize:mapped")
    compileOnly("ink.ptms.core:v11903:11903-minimize:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    jar {
        manifest.attributes["Main-Class"] = "me.adriianhdev.plutotems.PluTotems"
        val dependencies = configurations
            .runtimeClasspath
            .get()
            .map(::zipTree)
        from(dependencies)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}