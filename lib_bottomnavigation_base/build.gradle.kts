import com.clistery.src.baseArtifactId

plugins {
    id("kre-publish")
}

dependencies {
    implementation(libs.androidXCoreKtx)
    implementation(libs.androidXAppcompat)
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation("androidx.viewpager2:viewpager2:1.0.0")
    // compileOnly(libs.material120)
    // compileOnly(libs.material130)
    // compileOnly(libs.material140)
    // compileOnly(libs.material150)
    // compileOnly(libs.material160)
    // compileOnly(libs.material170)
    // compileOnly(libs.material180)
    // compileOnly(libs.material190)
    // compileOnly(libs.material1100)
}

afterEvaluate {
    publishing {
        publications {
            getByName<MavenPublication>("releaseAar") {
                artifactId = baseArtifactId
            }
        }
    }
}
