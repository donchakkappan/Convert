plugins {
    id("local.library")
}

android {
    namespace = "com.allutils.app_style_guide"
}

dependencies {

    api(libs.material)
    api(libs.composeMaterial)

    debugImplementation(libs.uiTooling)
    
    api(libs.lottie)

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}