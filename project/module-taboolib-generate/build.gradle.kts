plugins {
    id("io.izzel.taboolib") version taboolibGradlePluginVersion
}

taboolib {
    version = taboolibVersion
    installedTabooLibModules.forEach { install(it) }

    description {
        name = rootName

        contributors {
            name("Adriianh")
        }

        bukkitNodes = HashMap<String, Any>().apply {
            put("api-version", 1.13)
            put("built-date", currentISODate)
            put("built-by", systemUserName)
            put("built-os", systemOS)
        }
    }

    classifier = null
    options("skip-minimize", "keep-kotlin-module", "skip-taboolib-relocate")
}