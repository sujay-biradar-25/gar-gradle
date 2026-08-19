plugins {
    kotlin("jvm")
    application
}

dependencies {
    implementation(project(":core"))
    implementation(project(":javalib"))
    implementation(project(":kotlinlib"))
    // Needed on the app's own compile classpath: it reads RenderOutcome values produced by
    // code that came from the registry, and resolves the bridge transitively at runtime.
    implementation("ai.endor.gartest:vuln-bridge:1.0.0")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("ai.endor.mixed.app.AppKt")
}
