import com.clistery.src.exArtifactId

plugins {
    id("kre-publish")
}

dependencies {
    implementation(libs.androidXCoreKtx)
    implementation(libs.androidXAppcompat)
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_13x")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_14x")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_15x")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_16x")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_17x")))
}

afterEvaluate {
    publishing {
        publications {
            getByName<MavenPublication>("releaseAar") {
                artifactId = exArtifactId
            }
        }
    }
}
