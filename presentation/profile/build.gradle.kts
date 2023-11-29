plugins {
    id("plugin.tvmaniac.multiplatform")
    alias(libs.plugins.ksp)
}


kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(projects.core.traktAuth.api)
                implementation(projects.data.profile.api)
                implementation(projects.data.profilestats.api)

                implementation(libs.kotlinInject.runtime)
                implementation(libs.kotlinx.collections)
                implementation(libs.sqldelight.extensions)
                implementation(libs.voyager.core)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(projects.core.datastore.testing)
                implementation(projects.data.profile.testing)
                implementation(projects.core.traktAuth.testing)

                implementation(libs.coroutines.test)
                implementation(libs.kotest.assertions)
                implementation(libs.turbine)
            }
        }
    }
}

dependencies {
    add("kspIosX64", libs.kotlinInject.compiler)
    add("kspIosArm64", libs.kotlinInject.compiler)
}
