plugins {
    id("java-library")
    id("com.android.lint")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // For a description of the below dependencies, see the main project README
    compileOnly(libs.bundles.lint.api)
    testImplementation(libs.bundles.lint.tests)
}
