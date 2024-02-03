plugins {
    id("plugin.tvmaniac.multiplatform")
}


kotlin {
    sourceSets {
        commonMain {
            dependencies {
                api(projects.data.trailers.api)
                implementation(projects.core.database)
                implementation(projects.core.util)

                implementation(libs.coroutines.core)
            }
        }
    }
}