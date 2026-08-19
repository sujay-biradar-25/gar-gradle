plugins {
    java
}

// No GAR dependency. This module exists so the other modules have real internal call edges
// to make, rather than every call going straight into the third-party bridge.
java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}
