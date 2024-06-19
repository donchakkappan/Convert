plugins {
    id("dynamic.module")
}

android {
    namespace = "com.allutils.feature_scanner"
}

dependencies {
    implementation(projects.base)
    implementation(projects.app)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junitJupiterEngine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
