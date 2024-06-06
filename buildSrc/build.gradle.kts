plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(plugin(libs.plugins.kotlin.android))
    implementation(plugin(libs.plugins.kotlin.serialization))
    implementation(plugin(libs.plugins.kotlin.symbolProcessing))
    implementation(plugin(libs.plugins.android.application))
    implementation(plugin(libs.plugins.android.library))
    implementation(plugin(libs.plugins.spotless))
    implementation(plugin(libs.plugins.testLogger))
    implementation(plugin(libs.plugins.detekt))
    implementation(plugin(libs.plugins.junit5Android))
    implementation(plugin(libs.plugins.safeArgs))

    implementation(plugin(libs.plugins.android.application))
    implementation(plugin(libs.plugins.jetbrains.kotlin.android))
    implementation(plugin(libs.plugins.android.dynamic.feature))
    implementation(plugin(libs.plugins.android.library))
    implementation(plugin(libs.plugins.jetbrains.kotlin.jvm))

    implementation(plugin(libs.plugins.compose.compiler))
    implementation(plugin(libs.plugins.jetbrainsCompose))
}

kotlin {
    jvmToolchain(17)
}

fun plugin(plugin: Provider<PluginDependency>) =
    plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
