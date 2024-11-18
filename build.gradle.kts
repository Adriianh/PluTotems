import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-library`
    kotlin("jvm") version kotlinVersion
    id("com.github.johnrengelman.shadow") version shadowJarVersion
}

repositories {
    mavenCentral()
}

subprojects {

    applyPlugins()

    repositories {
        mavenCentral()

        /* TabooLib */
        maven("http://sacredcraft.cn:8081/repository/releases/") { isAllowInsecureProtocol = true }
        maven("http://mcitd.cn:8081/repository/releases/") { isAllowInsecureProtocol = true }

        /* Adventure & MiniMessage */
        maven("https://s01.oss.sonatype.org/content/groups/public/")
    }

    dependencies {
        compileOnly(kotlin("stdlib"))

        if (parent?.name == "project") {
            taboolibModules.forEach { compileTaboo(it) }
        }
    }

    java {
        withSourcesJar()
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
            freeCompilerArgs.addAll(
                "-Xallow-unstable-dependencies",
                "-Xjvm-default=all",
                "-Xextended-compiler-checks"
            )
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"

        /* Java */
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.shadowJar {
        /* Options */
        archiveAppendix.set("")
        archiveClassifier.set("")
        archiveVersion.set(rootVersion)
        destinationDirectory.set(file("$rootDir/bin"))

        relocate("taboolib", "$rootGroup.taboolib")

        dependencies {
            exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
            exclude(dependency("org.jetbrains.kotlin:kotlin-reflect"))
        }
    }

    group = rootGroup
    version = rootVersion

}

buildDirClean()