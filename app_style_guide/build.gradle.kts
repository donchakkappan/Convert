plugins {
    id("local.library")
}

android {
    namespace = "com.allutils.app_style_guide"
}

dependencies {

    api(libs.material)
    api(libs.composeMaterial)

    api(libs.bundles.compose)

    api(libs.constraintLayout)
    api(libs.composeConstraintLayout)

    debugImplementation(libs.uiTooling)
    
    api(libs.lottie)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junitJupiterEngine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}