package com.clistery.src

object AppConfig {
    const val compileSdk = 30
    const val buildToolsVersion = "30.0.3"
    const val minSdk = 14
    const val targetSdk = 30

    const val GROUP_ID = "io.github.clistery"
    const val ARTIFACT_ID = "bottomnavigationex-"
    const val versionName = "1.0.2"
    const val versionCode = 3

    const val baseArtifactId = ARTIFACT_ID.plus("base")
    const val exArtifactId = ARTIFACT_ID.plus("ex")
    const val ex130ArtifactId = ARTIFACT_ID.plus("130")
    const val ex140ArtifactId = ARTIFACT_ID.plus("140")

    const val base = "$GROUP_ID:${baseArtifactId}:${versionName}"
    const val ex = "$GROUP_ID:${exArtifactId}:${versionName}"
    const val ex_130 = "$GROUP_ID:${ex130ArtifactId}:${versionName}"
    const val ex_140 = "$GROUP_ID:${ex140ArtifactId}:${versionName}"
}