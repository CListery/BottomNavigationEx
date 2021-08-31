plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("clistery_plugin") {
            id = "com.clistery.gradle"
            implementationClass = "com.clistery.src.CPlugin"
        }
    }
}