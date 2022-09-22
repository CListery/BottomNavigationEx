plugins {
    id("com.android.application")
    id("android-common")
    id("kotlin-parcelize")
}

android {
    defaultConfig {
        val groupName: String by project
        val artifactName: String by project
        val targetSdkName: String by project
        val versionName: String by project
        val versionCode: String by project
        
        this.applicationId = "${groupName}.${artifactName}.demo"
        this.targetSdk = targetSdkName.toInt()
        this.versionName = versionName
        this.versionCode = versionCode.toInt()
        
        multiDexEnabled = true
        
        vectorDrawables.useSupportLibrary = true
    }
//    sourceSets {
//        named("main") {
////            kotlin.srcDirs("src/main/kotlin")
//            java.srcDirs("src/main/java", "src/main/kotlin")
//        }
//    }
}
