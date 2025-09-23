plugins {
    id("lib") apply true
    id("org.jetbrains.dokka")
    id("publish") apply true
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    val artifactId: String? by project
    moduleName.set(artifactId)
    moduleVersion.set(project.version.toString())
}

dokka {
    val artifactId: String? by project
    moduleName.set(artifactId)

    dokkaSourceSets.main {
        documentedVisibilities(
            org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier.Public,
            org.jetbrains.dokka.gradle.engine.parameters.VisibilityModifier.Protected,
        )

        perPackageOption {
            matchingRegex.set(".*internal.*")
            suppress.set(true)
        }
    }
    pluginsConfiguration {
        html {
            footerMessage.set("© 2025 Clistery")
        }
    }
}
