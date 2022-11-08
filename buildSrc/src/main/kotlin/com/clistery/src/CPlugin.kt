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
            val ex130ArtifactId = artifactName.plus("-130")
            val ex140ArtifactId = artifactName.plus("-140")
            val ex150ArtifactId = artifactName.plus("-150")
            val ex160ArtifactId = artifactName.plus("-160")
            val ex170ArtifactId = artifactName.plus("-170")
            
            project.extra.set("baseArtifactId", baseArtifactId)
            project.extra.set("exArtifactId", exArtifactId)
            project.extra.set("ex130ArtifactId", ex130ArtifactId)
            project.extra.set("ex140ArtifactId", ex140ArtifactId)
            project.extra.set("ex150ArtifactId", ex150ArtifactId)
            project.extra.set("ex160ArtifactId", ex160ArtifactId)
            project.extra.set("ex170ArtifactId", ex170ArtifactId)
            
            project.extra.set("base", "${groupName}:${baseArtifactId}:${versionName}")
            project.extra.set("ex", "${groupName}:${exArtifactId}:${versionName}")
            project.extra.set("_130", "${groupName}:${ex130ArtifactId}:${versionName}")
            project.extra.set("_140", "${groupName}:${ex140ArtifactId}:${versionName}")
            project.extra.set("_150", "${groupName}:${ex140ArtifactId}:${versionName}")
            project.extra.set("_160", "${groupName}:${ex140ArtifactId}:${versionName}")
            project.extra.set("_170", "${groupName}:${ex140ArtifactId}:${versionName}")
        }
        project.tasks.register("clean", Delete::class.java) {
            delete(project.buildDir)
        }
    }
}

val Project.lib_base: String get() = rootProject.extra.get("base").toString()
val Project.lib_ex: String get() = rootProject.extra.get("ex").toString()
val Project.lib_130: String get() = rootProject.extra.get("_130").toString()
val Project.lib_140: String get() = rootProject.extra.get("_140").toString()
val Project.lib_150: String get() = rootProject.extra.get("_150").toString()
val Project.lib_160: String get() = rootProject.extra.get("_160").toString()
val Project.lib_170: String get() = rootProject.extra.get("_170").toString()

val Project.baseArtifactId: String get() = rootProject.extra.get("baseArtifactId").toString()
val Project.exArtifactId: String get() = rootProject.extra.get("exArtifactId").toString()
val Project.ex130ArtifactId: String get() = rootProject.extra.get("ex130ArtifactId").toString()
val Project.ex140ArtifactId: String get() = rootProject.extra.get("ex140ArtifactId").toString()
val Project.ex150ArtifactId: String get() = rootProject.extra.get("ex150ArtifactId").toString()
val Project.ex160ArtifactId: String get() = rootProject.extra.get("ex160ArtifactId").toString()
val Project.ex170ArtifactId: String get() = rootProject.extra.get("ex170ArtifactId").toString()
