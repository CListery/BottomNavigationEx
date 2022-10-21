import com.clistery.src.*

plugins {
    id("app")
}

android {
    signingConfigs {
        register("release") {
            storeFile = file("key.jks")
            storePassword = "android"
            keyAlias = "android"
            keyPassword = "android"
        }
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    // leakcanary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-release:2.7")
    // Optional
    releaseImplementation("com.squareup.leakcanary:leakcanary-object-watcher-android:2.7")

    implementation(project(mapOf("path" to ":lib_bottomnavigationex")))
    implementation(project(mapOf("path" to ":lib_bottomnavigation_130")))
//    implementation(project(mapOf("path" to ":lib_bottomnavigation_140")))
//    implementation(lib_ex)
//    implementation(lib_130)
//    implementation(lib_140)
}