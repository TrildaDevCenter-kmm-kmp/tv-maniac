enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "tv-maniac"

include(
    ":android:app",
    ":android:common:annotations",
    ":android:common:compose",
    ":android:common:navigation",
    ":android:common:resources",
    ":android:features:discover",
    ":android:features:home",
    ":android:features:search",
    ":android:features:show-details",
    ":android:features:following",
    ":android:features:shows-grid",
    ":android:features:settings",
    ":android:features:seasons",
    ":shared:shared",
    ":shared:core:ui",
    ":shared:core:util",
    ":shared:core:test",
    ":shared:database",
    ":shared:remote",
    ":shared:domain:show-details:api",
    ":shared:domain:show-details:implementation",
    ":shared:domain:seasons:api",
    ":shared:domain:seasons:implementation",
    ":shared:domain:episodes:api",
    ":shared:domain:episodes:implementation",
    ":shared:domain:genre:api",
    ":shared:domain:genre:implementation",
    ":shared:domain:last-air-episodes:api",
    ":shared:domain:last-air-episodes:implementation",
    ":shared:domain:similar:api",
    ":shared:domain:similar:implementation",
    ":shared:domain:season-episodes:api",
    ":shared:domain:season-episodes:implementation",
    ":shared:domain:show-common:api",
    ":shared:domain:discover:api",
    ":shared:domain:discover:implementation",
    ":shared:domain:persistence",
    ":shared:core:firebase-config:api",
    ":shared:core:firebase-config:implementation",
)
