pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}

// Only Maven Central here. Google Artifact Registry is declared per project, with the
// artifactregistry:// scheme, so every module resolves it the way a developer machine does:
// through Google's plugin and Application Default Credentials.
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "gar-mixed"

include(":core", ":javalib", ":kotlinlib", ":app")
