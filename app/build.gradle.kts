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
    implementation(libs.androidXCoreKtx)
    implementation(libs.androidXAppcompat)
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    // leakcanary
    debugImplementation(libs.leakcanaryAndroid)
    releaseImplementation(libs.bundles.leakcanaryRel)

   implementation(project(mapOf("path" to ":lib_bottomnavigationex")))
    // implementation(lib_ex)
    
    // material 1.3.x
//    implementation(libs.material130)
//    implementation(project(mapOf("path" to ":lib_bottomnavigation_13x")))
//    implementation(lib_13x)
    
    // material 1.4.x
//    implementation(libs.material140)
//    implementation(project(mapOf("path" to ":lib_bottomnavigation_14x")))
//    implementation(lib_14x)
    
    // material 1.5.x
//    implementation(libs.material150)
//    implementation(project(mapOf("path" to ":lib_bottomnavigation_15x")))
//    implementation(lib_15x)
    
    // material 1.6.x
//    implementation(libs.material160)
//    implementation(libs.material161)
//    implementation(project(mapOf("path" to ":lib_bottomnavigation_16x")))
//    implementation(lib_16x)
    
    // material 1.7.x
    // implementation(libs.material170)
//    implementation(project(mapOf("path" to ":lib_bottomnavigation_17x")))
//     implementation(lib_17x)
    
    // material 1.8.x
    // implementation(libs.material180)
    // implementation(project(mapOf("path" to ":lib_bottomnavigation_18x")))
//     implementation(lib_18x)
    
    // material 1.9.x
    implementation(libs.material190)
    implementation(project(mapOf("path" to ":lib_bottomnavigation_19x")))
//     implementation(lib_19x)
}