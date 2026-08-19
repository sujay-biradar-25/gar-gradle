plugins {
    java
}

dependencies {
    implementation(project(":core"))
    implementation("ai.endor.gartest:vuln-bridge:1.0.0")
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}
