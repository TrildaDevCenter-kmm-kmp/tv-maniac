package com.thomaskioko.tvmaniac.plugins

import com.android.build.gradle.LibraryExtension
import com.thomaskioko.tvmaniac.extensions.Versions
import com.thomaskioko.tvmaniac.extensions.configureAndroid
import com.thomaskioko.tvmaniac.extensions.configureFlavors
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_18
                    targetCompatibility = JavaVersion.VERSION_18
                }

                configureAndroid()
                configureFlavors(this)
                defaultConfig.targetSdk = Versions.TARGET_SDK
            }
        }
    }
}
