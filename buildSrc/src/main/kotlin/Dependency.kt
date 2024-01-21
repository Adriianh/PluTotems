@file:Suppress("DEPRECATION")

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.plugins.PluginAware
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.authentication.http.BasicAuthentication
import org.gradle.kotlin.dsl.*

fun PluginAware.applyPlugins() {
    apply(plugin = "maven-publish")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "org.jetbrains.kotlin.jvm")
}

fun PublishingExtension.createPublish(project: Project) {
    repositories {
        maven(repoTabooProject) {
            credentials {
                username = project.findProperty("taboolibUsername").toString()
                password = project.findProperty("taboolibPassword").toString()
            }
            authentication { create<BasicAuthentication>("basic") }
        }
        mavenLocal()
    }

    publications {
        create<MavenPublication>("maven") {
            artifactId = project.name
            groupId = rootGroup
            version = rootVersion

            artifact(project.tasks["kotlinSourcesJar"])
            artifact(project.tasks["shadowJar"]) { classifier = null }
            println("> Apply \"$groupId:$artifactId:$version\"")
        }
    }

}

fun Project.initSubProject(publish: Project.() -> Unit) {
    group = rootGroup
    version = rootVersion

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    if (parent?.name != "PluTotems") {
        buildDirClean()
    }
    if (project.parent?.name == "project" || project.name == "project") {
        publish()
    }
}

fun Project.buildDirClean() {
    gradle.buildFinished { buildDir.deleteRecursively() }
}

fun RepositoryHandler.projectRepositories() {
    maven(repoTabooProject) { isAllowInsecureProtocol = true }
    mavenCentral()

    maven(url = "https://repo.codemc.io/repository/maven-public/")
    maven(url = "https://jitpack.io/")
    maven(url = "https://repo.auxilor.io/repository/maven-public/")
}

fun DependencyHandler.compileLocal(project: Project, vararg dir: String) {
    dir.forEach { add("compileOnly", project.fileTree(it)) }
}

fun DependencyHandler.compileModule(vararg name: String) {
    name.forEach { add("compileOnly", project(":project:$it")) }
}

fun DependencyHandler.implementationModule(vararg name: String) {
    name.forEach { add("implementation", project(":project:$it")) }
}

fun DependencyHandler.compileNMS() {
    add("compileOnly", "ink.ptms:nms-all:1.0.0")
}

fun DependencyHandler.compileCore(
    version: Int,
    minimize: Boolean = true,
    mapped: Boolean = false,
    complete: Boolean = false,
) {
    val notation =
        "ink.ptms.core:v$version:$version${if (!complete && minimize) "-minimize" else ""}${if (complete) "" else if (mapped) ":mapped" else ":universal"}"
    add("compileOnly", notation)
}

fun DependencyHandler.installTaboo(vararg module: String, version: String = taboolibVersion) = module.forEach {
    add("compileOnly", "io.izzel.taboolib:$it:$version")
}

fun DependencyHandler.shadowTaboo(vararg module: String, version: String = taboolibVersion) = module.forEach {
    add("implementation", "io.izzel.taboolib:$it:$version")
}

fun DependencyHandler.compileTabooLib() {
    installedTabooLibModules.forEach { installTaboo(it) }
}