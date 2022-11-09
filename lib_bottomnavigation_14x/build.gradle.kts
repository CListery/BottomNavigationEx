import com.clistery.src.ex14xArtifactId

plugins {
    id("kre-publish")
}

dependencies {
    implementation(libs.androidXCoreKtx)
    implementation(libs.androidXAppcompat)
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    
    api(project(mapOf("path" to ":lib_bottomnavigation_base")))
    api(libs.material140)
}

afterEvaluate {
    publishing {
        publications {
            getByName<MavenPublication>("releaseAar") {
                artifactId = ex14xArtifactId
            }
        }
    }
}
