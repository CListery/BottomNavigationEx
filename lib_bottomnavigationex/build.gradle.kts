import com.clistery.src.exArtifactId

plugins {
    id("kre-publish")
}

dependencies {
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_130")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_140")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_150")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_160")))
    compileOnly(project(mapOf("path" to ":lib_bottomnavigation_170")))
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
