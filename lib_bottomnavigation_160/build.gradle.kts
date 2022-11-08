import com.clistery.src.ex160ArtifactId

plugins {
    id("kre-publish")
}

dependencies {
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")
    
    api(project(mapOf("path" to ":lib_bottomnavigation_base")))
    api("com.google.android.material:material:1.6.0")
}

afterEvaluate {
    publishing {
        publications {
            getByName<MavenPublication>("releaseAar") {
                artifactId = ex160ArtifactId
            }
        }
    }
}
