plugins {
    id("com.android.library")
    id("android-common")
    id("kotlin-parcelize")
}

android {
    val targetSdkName: String by project
    defaultConfig {
        this.targetSdk = targetSdkName.toInt()
        
        vectorDrawables.useSupportLibrary = true
    }
    lint {
        abortOnError = false
    }
//    sourceSets {
//        named("main") {
////            kotlin.srcDirs("src/main/kotlin")
//            java.srcDirs("src/main/java", "src/main/kotlin")
//        }
//    }
}
