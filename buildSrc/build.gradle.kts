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
    implementation("com.android.tools.build:gradle:7.2.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.7.10")
    implementation("org.jfrog.buildinfo:build-info-extractor-gradle:4.29.0")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.7.10")
}

gradlePlugin {
    plugins {
        create("clistery_plugin") {
            id = "com.clistery.gradle"
            implementationClass = "com.clistery.src.CPlugin"
        }
    }
}
