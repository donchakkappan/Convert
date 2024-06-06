plugins {
    id("local.library")
}

android {
    namespace = "com.allutils.feature_documents"
}

dependencies {
    implementation(projects.base)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}