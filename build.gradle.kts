apply(from = "publishToMaven.gradle")
apply(from = "publishToProject.gradle")

plugins {
    id("com.clistery.gradle")
    id("org.jetbrains.dokka")
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.androidBuildToolGradle)
    }
}

dokka {
    dokkaPublications.html {
        suppressInheritedMembers.set(true)
        failOnWarning.set(true)
        outputDirectory.set(layout.projectDirectory.dir("docs"))

        failOnWarning.set(false)
        suppressObviousFunctions.set(true)
        suppressInheritedMembers.set(false)
        offlineMode.set(false)
    }
    pluginsConfiguration {
        html {
            footerMessage.set("© 2025 Clistery")
        }
    }
}

dependencies {
    dokka(project(":lib_bottomnavigationex:"))
    dokka(project(":lib_bottomnavigation_base:"))
    dokka(project(":lib_bottomnavigation_13x:"))
    dokka(project(":lib_bottomnavigation_14x:"))
    dokka(project(":lib_bottomnavigation_15x:"))
    dokka(project(":lib_bottomnavigation_16x:"))
    dokka(project(":lib_bottomnavigation_17x:"))
    dokka(project(":lib_bottomnavigation_18x:"))
    dokka(project(":lib_bottomnavigation_19x:"))
    dokka(project(":lib_bottomnavigation_110x:"))
}
