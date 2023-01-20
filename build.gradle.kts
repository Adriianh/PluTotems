plugins {
    `java-library`
    `maven-publish`
    id("io.izzel.taboolib") version "1.53"
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
    install("platform-bukkit")
    classifier = null
    version = "6.0.10-42"

    relocate("ink.ptms.um", "$group.um")

    description {
        contributors {
            name("Adriianh")
            desc("Create your own totems with custom properties")
        }
        prefix("PluTotems")
    }
}

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://repo.tabooproject.org/repository/releases")
}

dependencies {
    compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
    implementation("xyz.xenondevs:particle:1.8.3")

    taboo("ink.ptms:um:1.0.0-beta-23")
    compileOnly("ink.ptms.core:v11903:11903-minimize:mapped")
    compileOnly("ink.ptms.core:v11903:11903-minimize:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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