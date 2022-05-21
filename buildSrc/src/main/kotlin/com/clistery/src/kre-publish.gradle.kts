import org.gradle.kotlin.dsl.extra

plugins {
    `maven-publish`
    signing
}

val signingKeyId = extra.get("signing_keyId")?.toString()!!
val signingSecretKeyRingFile = extra.get("signing_secretKeyRingFile")?.toString()!!
val signingPassword = extra.get("signing_password")?.toString()!!

val nexusPro = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
val nexusSnapshot = "https://s01.oss.sonatype.org/content/repositories/snapshots/"

val nexusUsername = extra.get("sonatype_user_name")?.toString()!!
val nexusPassword = extra.get("sonatype_user_pwd")?.toString()!!

val isReleaseVersion = !version.toString().endsWith("SNAPSHOT")

project.extra.set("signing.keyId", signingKeyId)
project.extra.set("signing.secretKeyRingFile", signingSecretKeyRingFile)
project.extra.set("signing.password", signingPassword)

publishing {
    publications.configureEach {
        if (this is MavenPublication) {
            val publicName = "${rootProject.name} ${name.capitalize()}"
            pom {
                name.set(publicName)
                description.set("An android lib for enhancing BottomNavigationView.(Extended from https://github.com/ittianyu/BottomNavigationViewEx)")
                url.set("https://github.com/CListery/BottomNavigationEx")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("cyh")
                        name.set("CListery")
                        email.set("cai1083088795@gmail.com")
                    }
                }
                scm {
                    url.set("https://github.com/CListery/BottomNavigationEx")
                    connection.set("scm:git@github.com:CListery/BottomNavigationEx.git")
                    developerConnection.set("scm:git@github.com:CListery/BottomNavigationEx.git")
                }
                withXml {
                    val allDependenciesNode = asNode().appendNode("dependencies")
                    println("configurations: ${configurations.names}")
                    configurations.named("api").get().allDependencies.forEach {
                        val group = it.group
                        val name = it.name
                        val ver = it.version
                        val reason = it.reason
                        print("group:${group}, name:${name}, ver:${ver}, reason:${reason}")
                        if(group.isNullOrEmpty() || name.isEmpty() || ver.isNullOrEmpty()){
                            println(" --> Fail")
                            return@forEach
                        }
                        println(" --> OK")
                        val dependencyNode = allDependenciesNode.appendNode("dependency")
                        dependencyNode.appendNode("groupId", group)
                        dependencyNode.appendNode("artifactId", name)
                        dependencyNode.appendNode("version", ver)
                    }
                }
            }
        }
    }

    repositories {
        maven {
            name = "_sonatype_"
            val releasesRepoUrl = uri(nexusPro)
            val snapshotsRepoUrl = uri(nexusSnapshot)
            url = if (isReleaseVersion) releasesRepoUrl else snapshotsRepoUrl
            credentials {
                username = nexusUsername
                password = nexusPassword
            }
        }
        maven {
            name = "_ProjectMaven_"
            url = uri(extra.get("PROJECT_LOCAL_MAVEN_PATH")?.toString()!!)
        }
        maven {
            name = "_jfrog.fx_"
            url = uri(extra.get("MAVEN_REPOSITORY_URL")?.toString()!!)
            isAllowInsecureProtocol = true
            credentials {
                username = extra.get("artifactory_maven_user")?.toString()!!
                password = extra.get("artifactory_maven_pwd")?.toString()!!
            }
        }
    }
}

tasks.withType<Sign>().configureEach {
    onlyIf { isReleaseVersion }
}

signing {
    sign(publishing.publications)
}