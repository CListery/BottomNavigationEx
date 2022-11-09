apply(from = "publishToMaven.gradle")
apply(from = "publishToProject.gradle")

plugins {
    id("com.clistery.gradle")
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
