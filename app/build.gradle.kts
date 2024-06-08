import com.android.build.api.dsl.ApplicationDefaultConfig
import java.util.Locale

plugins {
    id("local.app")
}

android {
    namespace = "com.allutils.convert"

    val catalogs = extensions.getByType<VersionCatalogsExtension>()
    val libs = catalogs.named("libs")
    compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

    defaultConfig {
        applicationId = "com.allutils.convert"
        minSdk = libs.findVersion("minSdk").get().toString().toInt()
        targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigFieldFromGradleProperty("currencyApiBaseURL")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
//    dynamicFeatures += setOf(
//        ":feature_currency",
//        ":feature_units",
//        ":feature_emi",
//        ":feature_media",
//        ":feature_documents",
//        ":feature_scanner"
//    )
}

dependencies {

    implementation(projects.base)

    implementation(projects.featureCurrency)
    implementation(projects.featureUnits)
    implementation(projects.featureEmi)
    implementation(projects.featureMedia)
    implementation(projects.featureDocuments)
    implementation(projects.featureScanner)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}

fun ApplicationDefaultConfig.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".uppercase(Locale.getDefault())
    buildConfigField("String", androidResourceName, propertyValue)
}

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.lowercase(Locale.getDefault()) }
