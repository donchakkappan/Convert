plugins {
    id("local.library")
}

android {
    namespace = "com.allutils.feature_emi"
}

dependencies {
    implementation(projects.base)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}