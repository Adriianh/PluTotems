import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version shadowJarVersion
}

dependencies {
    compileTabooLib()
    compileCore(12002)

    rootProject
        .childProjects["project"]!!
        .childProjects
        .values
        .forEach { implementation(it) }
}

tasks {
    withType<ShadowJar> {
        /* Options */
        archiveAppendix.set("")
        archiveClassifier.set("")
        archiveVersion.set(rootVersion)
        archiveBaseName.set(rootName)
        /* Exclude */
        exclude("META-INF/**")
        /* TabooLib */
        relocate("taboolib", "$rootGroup.taboolib")
        relocate("org.tabooproject", "$rootGroup.taboolib.library")
        /* NBT-API */
        relocate("de.tr7zw.changeme.nbtapi", "$rootGroup.nbtapi")
        /* Kotlin */
        relocate("kotlin.", "kotlin1921.") { exclude("kotlin.Metadata") }
    }
    build {
        dependsOn(shadowJar)
    }
}