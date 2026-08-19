plugins {
    java
    kotlin("jvm") version "2.0.21" apply false
    id("com.google.cloud.artifactregistry.gradle-plugin") version "2.2.1" apply false
}

// Every project, the root included, reaches GAR through the artifactregistry:// scheme with
// Google's plugin applied. That plugin resolves the scheme with Application Default
// Credentials, which is what a developer has and a scan does not.
allprojects {
    group = "ai.endor.mixed"
    version = "1.0.0"

    apply(plugin = "com.google.cloud.artifactregistry.gradle-plugin")

    repositories {
        maven {
            name = "GarArtifactRegistry"
            url = uri("artifactregistry://us-central1-maven.pkg.dev/gar-scope-test-sb/scope-test")
        }
        mavenCentral()
    }
}

// The root is a real module with its own sources and its own jar, not an empty aggregator.
dependencies {
    implementation(project(":core"))
    implementation("ai.endor.gartest:vuln-bridge:1.0.0")
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}
