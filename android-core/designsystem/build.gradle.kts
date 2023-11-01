plugins {
    id("tvmaniac.compose.library")
}

android {
    namespace = "com.thomaskioko.tvmaniac.compose"
}

dependencies {
    api(libs.coroutines.jvm)
    api(libs.coil.coil)
    api(libs.coil.compose)
    api(libs.androidx.compose.ui.tooling)
    api(libs.androidx.palette)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.ui)

    implementation(projects.androidCore.resources)

    implementation(libs.kenburns)
    implementation(libs.androidx.core)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material.icons)
}
