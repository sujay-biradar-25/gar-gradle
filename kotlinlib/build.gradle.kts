plugins {
    kotlin("jvm")
}

dependencies {
    implementation(project(":core"))
    implementation("ai.endor.gartest:vuln-bridge:1.0.0")
}

kotlin {
    jvmToolchain(17)
}
