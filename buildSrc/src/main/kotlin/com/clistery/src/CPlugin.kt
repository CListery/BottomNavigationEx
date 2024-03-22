package com.clistery.src

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.provideDelegate

internal val Project.isRoot get() = this == this.rootProject

class CPlugin : Plugin<Project> {
    
    override fun apply(project: Project) {
        if (project.isRoot) {
            val groupName: String by project
            val artifactName: String by project
            val versionName: String by project
            
            val baseArtifactId = artifactName.plus("-base")
            val exArtifactId = artifactName.plus("-ex")
            val ex13xArtifactId = artifactName.plus("-13x")
            val ex14xArtifactId = artifactName.plus("-14x")
            val ex15xArtifactId = artifactName.plus("-15x")
            val ex16xArtifactId = artifactName.plus("-16x")
            val ex17xArtifactId = artifactName.plus("-17x")
            val ex18xArtifactId = artifactName.plus("-18x")
            val ex19xArtifactId = artifactName.plus("-19x")
            
            project.extra.set("baseArtifactId", baseArtifactId)
            project.extra.set("exArtifactId", exArtifactId)
            project.extra.set("ex13xArtifactId", ex13xArtifactId)
            project.extra.set("ex14xArtifactId", ex14xArtifactId)
            project.extra.set("ex15xArtifactId", ex15xArtifactId)
            project.extra.set("ex16xArtifactId", ex16xArtifactId)
            project.extra.set("ex17xArtifactId", ex17xArtifactId)
            project.extra.set("ex18xArtifactId", ex18xArtifactId)
            project.extra.set("ex19xArtifactId", ex19xArtifactId)
            
            project.extra.set("base", "${groupName}:${baseArtifactId}:${versionName}")
            project.extra.set("ex", "${groupName}:${exArtifactId}:${versionName}")
            project.extra.set("_13x", "${groupName}:${ex13xArtifactId}:${versionName}")
            project.extra.set("_14x", "${groupName}:${ex14xArtifactId}:${versionName}")
            project.extra.set("_15x", "${groupName}:${ex15xArtifactId}:${versionName}")
            project.extra.set("_16x", "${groupName}:${ex16xArtifactId}:${versionName}")
            project.extra.set("_17x", "${groupName}:${ex17xArtifactId}:${versionName}")
            project.extra.set("_18x", "${groupName}:${ex18xArtifactId}:${versionName}")
            project.extra.set("_19x", "${groupName}:${ex19xArtifactId}:${versionName}")
        }
        project.tasks.register("clean", Delete::class.java) {
            delete(project.buildDir)
        }
    }
}

val Project.lib_base: String get() = rootProject.extra.get("base").toString()
val Project.lib_ex: String get() = rootProject.extra.get("ex").toString()
val Project.lib_13x: String get() = rootProject.extra.get("_13x").toString()
val Project.lib_14x: String get() = rootProject.extra.get("_14x").toString()
val Project.lib_15x: String get() = rootProject.extra.get("_15x").toString()
val Project.lib_16x: String get() = rootProject.extra.get("_16x").toString()
val Project.lib_17x: String get() = rootProject.extra.get("_17x").toString()
val Project.lib_18x: String get() = rootProject.extra.get("_18x").toString()
val Project.lib_19x: String get() = rootProject.extra.get("_19x").toString()

val Project.baseArtifactId: String get() = rootProject.extra.get("baseArtifactId").toString()
val Project.exArtifactId: String get() = rootProject.extra.get("exArtifactId").toString()
val Project.ex13xArtifactId: String get() = rootProject.extra.get("ex13xArtifactId").toString()
val Project.ex14xArtifactId: String get() = rootProject.extra.get("ex14xArtifactId").toString()
val Project.ex15xArtifactId: String get() = rootProject.extra.get("ex15xArtifactId").toString()
val Project.ex16xArtifactId: String get() = rootProject.extra.get("ex16xArtifactId").toString()
val Project.ex17xArtifactId: String get() = rootProject.extra.get("ex17xArtifactId").toString()
val Project.ex18xArtifactId: String get() = rootProject.extra.get("ex18xArtifactId").toString()
val Project.ex19xArtifactId: String get() = rootProject.extra.get("ex19xArtifactId").toString()
