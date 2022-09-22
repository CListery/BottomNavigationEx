import com.clistery.src.baseArtifactId

plugins {
    id("kre-publish")
}

dependencies {
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.appcompat:appcompat:1.2.0")
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

    implementation("androidx.viewpager2:viewpager2:1.0.0")
    compileOnly("com.google.android.material:material:1.3.0")
    compileOnly("com.google.android.material:material:1.4.0")
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
