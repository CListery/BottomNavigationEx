plugins {
    `maven-publish`
    signing
    id("com.android.library") apply false
}

val isReleaseVersion get() = !version.toString().endsWith("SNAPSHOT")

val androidSourcesJar by tasks.register<Jar>("androidSourcesJar") {
    from(android.sourceSets.flatMap { it.java.srcDirs })
    archiveClassifier.set("sources")
}

publishing {
    repositories {
        maven {
            val sonatypeRepoRelease: String by project
            val sonatypeRepoSnapshot: String by project
            val sonatypeUserName: String by project
            val sonatypeUserPwd: String by project
            
            name = "_SONATYPE_"
            url = uri(if (isReleaseVersion) sonatypeRepoRelease else sonatypeRepoSnapshot)
            credentials {
                username = sonatypeUserName
                password = sonatypeUserPwd
            }
        }
        maven {
            val projectLocalMavenPath: String by project
            name = "_PROJECT-MAVEN_"
            url = uri(projectLocalMavenPath)
        }
        maven {
            val mavenRepositoryUrl: String by project
            val artifactoryUserName: String by project
            val artifactoryUserPwd: String by project
            name = "_JFROG.FS_"
            url = uri(mavenRepositoryUrl)
            isAllowInsecureProtocol = true
            credentials {
                username = artifactoryUserName
                password = artifactoryUserPwd
            }
        }
    }
    publications.withType<MavenPublication>().configureEach {
        val groupName: String? by project
        if (!groupName.isNullOrEmpty()) {
            this.groupId = groupName
        }
        val artifactName: String? by project
        if (!artifactName.isNullOrEmpty()) {
            this.artifactId = artifactName
        }
        val versionName: String? by project
        if (!versionName.isNullOrEmpty()) {
            this.version = versionName
        }
        
        val pomDescription: String by project
        val pomScmUrl: String? by project
        val pomUrl: String? by project
        val pomScmConnection: String? by project
        val pomScmDevConnection: String? by project
        val pomLicenseName: String? by project
        val pomLicenseUrl: String? by project
        val pomLicenseDist: String? by project
        val pomDeveloperId: String? by project
        val pomDeveloperName: String? by project
        val pomDeveloperEmail: String? by project
        
        suppressAllPomMetadataWarnings()
        
        pom {
            name.set(rootProject.name)
            description.set(pomDescription)
            url.set(pomUrl)
            licenses {
                license {
                    name.set(pomLicenseName)
                    url.set(pomLicenseUrl)
                    distribution.set(pomLicenseDist)
                }
            }
            developers {
                developer {
                    id.set(pomDeveloperId)
                    name.set(pomDeveloperName)
                    email.set(pomDeveloperEmail)
                }
            }
            scm {
                url.set(pomScmUrl)
                connection.set(pomScmConnection)
                developerConnection.set(pomScmDevConnection)
            }
        }
        artifact(androidSourcesJar)
    }
}

afterEvaluate {
    publishing {
        publications {
            maybeCreate<MavenPublication>("releaseAar").apply {
                from(components["release"])
            }
        }
    }
}

project.extra.set("signing.keyId", project.property("signing_keyId"))
project.extra.set("signing.password", project.property("signing_password"))
project.extra.set("signing.secretKeyRingFile", project.property("signing_secretKeyRingFile"))

signing {
    sign(publishing.publications)
}

tasks.withType<Sign>().configureEach {
    onlyIf { isReleaseVersion }
}
