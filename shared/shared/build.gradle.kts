
import org.jetbrains.kotlin.gradle.plugin.mpp.BitcodeEmbeddingMode
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import util.libs

plugins {
    `kmm-domain-plugin`
    kotlin("plugin.serialization") version ("1.6.10")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

version = libs.versions.shared.module.version.get()

android {
    namespace = "com.thomaskioko.tvmaniac.shared"
}

kotlin {

    val xcf = XCFramework()
    ios {
        binaries.framework {
            baseName = "TvManiac"
            xcf.add(this)

            linkerOpts("-F${rootProject.projectDir}/ios/Carthage/Build/iOS/")
            linkerOpts("-ObjC")
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            isStatic = false
            linkerOpts.add("-lsqlite3")

            export(project(":shared:core:firebase-config:api"))
            export(projects.shared.core.ui)
            export(projects.shared.core.util)
            export(projects.shared.database)
            export(projects.shared.remote)
            export(projects.shared.domain.showDetails.api)
            export(projects.shared.domain.seasons.api)
            export(projects.shared.domain.episodes.api)
            export(projects.shared.domain.genre.api)
            export(projects.shared.domain.lastAirEpisodes.api)
            export(projects.shared.domain.similar.api)
            export(projects.shared.domain.seasonEpisodes.api)
            export(projects.shared.domain.showCommon.api)
            export(projects.shared.domain.discover.api)
            export(projects.shared.domain.persistence)
            embedBitcode(BitcodeEmbeddingMode.BITCODE)

            transitiveExport = true
        }
    }
}

dependencies {
    commonMainApi(projects.shared.core.ui)
    commonMainApi(projects.shared.core.util)
    commonMainApi(project(":shared:core:firebase-config:api"))
    commonMainApi(projects.shared.database)
    commonMainApi(projects.shared.remote)
    commonMainApi(projects.shared.domain.showDetails.api)
    commonMainApi(projects.shared.domain.seasons.api)
    commonMainApi(projects.shared.domain.episodes.api)
    commonMainApi(projects.shared.domain.genre.api)
    commonMainApi(projects.shared.domain.lastAirEpisodes.api)
    commonMainApi(projects.shared.domain.similar.api)
    commonMainApi(projects.shared.domain.seasonEpisodes.api)
    commonMainApi(projects.shared.domain.showCommon.api)
    commonMainApi(projects.shared.domain.discover.api)
    commonMainApi(projects.shared.domain.persistence)

    commonMainImplementation(projects.shared.domain.episodes.implementation)
    commonMainImplementation(project(":shared:core:firebase-config:implementation"))
    commonMainImplementation(projects.shared.domain.showDetails.implementation)
    commonMainImplementation(projects.shared.domain.seasons.implementation)
    commonMainImplementation(projects.shared.domain.genre.implementation)
    commonMainImplementation(projects.shared.domain.lastAirEpisodes.implementation)
    commonMainImplementation(projects.shared.domain.similar.implementation)
    commonMainImplementation(projects.shared.domain.seasonEpisodes.implementation)
    commonMainImplementation(projects.shared.domain.discover.implementation)

    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.kotlin.coroutines.core)
}

multiplatformSwiftPackage {
    packageName("TvManiac")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }

    distributionMode { local() }
    outputDirectory(File("$projectDir/../../../", "tvmaniac-swift-packages"))
}
