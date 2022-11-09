import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * @see [PluginSpecBuilders]
 */
plugins {
    id("kotlin-kapt")
    id("kotlin-android")
}

extensions.configure(com.android.build.api.dsl.CommonExtension::class) {
    val compileSdkName: String by project
    val minSdkName: String by project
    
    this.compileSdk = compileSdkName.toInt()
    
    defaultConfig.apply {
        minSdk = minSdkName.toInt()
        //        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "androidx.test.ext.junit.runners.AndroidJUnit4"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/AL2.0"
            excludes += "/META-INF/LGPL2.1"
            excludes += "/META-INF/LICENSE*"
            excludes += "/META-INF/CHANGES"
            excludes += "/META-INF/README*"
            excludes += "/META-INF/NOTICE*"
            pickFirsts += "/META-INF/INDEX*"
        }
    }
    lint {
        disable += "ParcelCreator"
        disable += "GoogleAppIndexingWarning"
        quiet = false
        abortOnError = false
        ignoreWarnings = true
        xmlReport = false
        htmlReport = true
        htmlOutput = project.file("${project.buildDir}/reports/lint/lint-reports.html")
        xmlOutput = project.file("${project.buildDir}/reports/lint/lint-reports.xml")
        checkDependencies = true
        ignoreTestSources = true
    }
    sourceSets.named("main") {
        java.srcDirs("src/main/java", "src/main/kotlin")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}