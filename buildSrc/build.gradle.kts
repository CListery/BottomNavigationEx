plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

dependencies {
    implementation(gradleApi())
    implementation(gradleKotlinDsl())
    implementation(buildSrcLibs.androidBuildToolGradle)
    implementation(buildSrcLibs.jetbrainsKotlinGradle)
    implementation(buildSrcLibs.jetbrainsKotlinAndroidGradle)
    implementation(buildSrcLibs.jfrogBuildInfoExtractorGradle)
    implementation(buildSrcLibs.dokkaGradle)
}

gradlePlugin {
    plugins {
        create("clistery_plugin") {
            id = "com.clistery.gradle"
            implementationClass = "com.clistery.src.CPlugin"
        }
    }
}
