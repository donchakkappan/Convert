pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Convert"
include(":app")
include(":feature_currency")
include(":feature_units")
include(":feature_emi")
include(":feature_media")
include(":feature_documents")
include(":feature_scanner")
include(":base")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app_style_guide")
include(":lint_rules")
