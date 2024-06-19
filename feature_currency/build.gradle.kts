import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.DynamicFeatureDefaultConfig
import com.android.build.api.dsl.LibraryDefaultConfig
import java.util.Locale

plugins {
    id("dynamic.module")
}

android {
    namespace = "com.allutils.feature_currency"

    defaultConfig {
        buildConfigFieldFromGradleProperty("currencyApiBaseURL")
        buildConfigFieldFromGradleProperty("currencyApiKey")
    }
}

dependencies {
    implementation(projects.base)
    implementation(projects.app)

    debugImplementation(libs.uiTooling)

    ksp(libs.roomCompiler)

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junitJupiterEngine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

fun DynamicFeatureDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".uppercase(Locale.getDefault())
    buildConfigField("String", androidResourceName, propertyValue)
}

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.lowercase(Locale.getDefault()) }
