files("../gradle/libs.versions.toml").also { catalogVersionsFile->
    if (true == catalogVersionsFile.singleOrNull()?.exists()) {
        dependencyResolutionManagement {
            versionCatalogs {
                create("buildSrcLibs") {
                    from(catalogVersionsFile)
                }
            }
        }
    }
}