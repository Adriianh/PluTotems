import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.serialization() {
    add("compileOnly", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
}