plugins {
    id("local.library")
}

android {
    namespace = "com.allutils.feature_units"
}

dependencies {
    implementation(projects.base)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junitJupiterEngine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}