const val rootName = "PluTotems"
const val rootGroup = "com.github.adriianh"
const val rootVersion = "2.0.0"

val installedTabooLibModules = setOf(
    "common",
    "common-5",
    "platform-bukkit",
    "module-kether",
    "module-configuration",
    "module-lang",
    "module-chat",
    "module-ai"
)

const val kotlinVersion = "1.9.21"
const val shadowJarVersion = "8.1.1"
const val repoTabooProject = "http://ptms.ink:8081/repository/releases"

val taboolibGradlePluginVersion = latestTabooLibPluginVersion
    .also { println("Using taboolib-gradle-plugin-version = $it") }
val taboolibVersion = latestTabooLibVersion
    .also { println("Using taboolib-version = $it") }