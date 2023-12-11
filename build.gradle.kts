plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.56"
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
}

taboolib {
    install(
        "common",
        "common-5",
        "module-chat",
        "module-configuration",
        "module-kether",
        "module-lang",
        "module-nms", "module-nms-util",
        "module-navigation", "module-ai",
        "platform-bukkit"
    )

    classifier = null
    version = "6.0.12-40"

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
    mavenLocal()
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.tabooproject.org/repository/releases/")
    maven("https://eldonexus.de/repository/maven-public")
    maven("https://mvn.lumine.io/repository/maven-public")
    maven("https://repo.xenondevs.xyz/releases")
}

dependencies {
    implementation("xyz.xenondevs:particle:1.8.4")
    implementation("de.eldoria.util:eldo-util:2.0.3")
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
    implementation("com.github.cryptomorin:XSeries:9.7.0") { isTransitive = false }

    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
    compileOnly("io.lumine:Mythic-Dist:5.3.5")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core:2.5.2")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit:2.5.2") { isTransitive = false }

    taboo("ink.ptms:um:1.0.0-beta-33")
    compileOnly("ink.ptms.core:v12002:12002:mapped")
    compileOnly("ink.ptms.core:v12002:12002:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
    implementation(kotlin("reflect"))
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
