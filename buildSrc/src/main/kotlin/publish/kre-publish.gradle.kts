plugins {
    id("lib") apply true
    id("org.jetbrains.dokka")
    id("publish") apply true
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    val artifactId: String? by project
    moduleName.set(artifactId)
    
    dokkaSourceSets {
        configureEach {
            skipDeprecated.set(true)
            skipEmptyPackages.set(true)
            //            noStdlibLink.set(true)
            //            noAndroidSdkLink.set(true)
            //            noJdkLink.set(true)
            //            includeNonPublic.set(true)
        }
    }
    offlineMode.set(true)
}

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.get().outputDirectory)
    archiveClassifier.set("javadoc")
}

publishing {
    publications.withType<MavenPublication>().configureEach {
        artifact(javadocJar)
    }
}

tasks.register<org.jetbrains.dokka.gradle.DokkaTask>("dokkaDocs") {
    dependsOn(tasks.dokkaJekyll)
    outputDirectory.set(file("$rootDir/docs"))
}
