plugins {
    alias(libs.plugins.tvmaniac.application)
}

tvmaniac {
    app {
        applicationId("com.thomaskioko.tvmaniac")
        minify(
            rootProject.file("app/proguard-rules.pro"),
        )
    }

    android {
        useKspAnvil()
        useCompose()
        useBaselineProfile()
        useManagedDevices()
    }
}

dependencies {
    implementation(projects.androidDesignsystem)
    implementation(projects.androidFeature.home)
    implementation(projects.androidFeature.moreShows)
    implementation(projects.androidFeature.seasonDetails)
    implementation(projects.androidFeature.showDetails)
    implementation(projects.androidFeature.trailers)
    implementation(projects.api.tmdb.api)
    implementation(projects.api.tmdb.implementation)
    implementation(projects.core.base)
    implementation(projects.core.util)
    implementation(projects.core.locale.implementation)
    implementation(projects.navigation.api)
    implementation(projects.navigation.implementation)
    implementation(projects.core.logger.api)
    implementation(projects.core.logger.implementation)
    implementation(projects.data.cast.api)
    implementation(projects.data.cast.implementation)
    implementation(projects.data.episodes.api)
    implementation(projects.data.episodes.implementation)
    implementation(projects.data.featuredshows.api)
    implementation(projects.data.featuredshows.implementation)
    implementation(projects.data.genre.api)
    implementation(projects.data.genre.implementation)
    implementation(projects.data.traktauth.api)
    implementation(projects.data.traktauth.implementation)
    implementation(projects.data.watchlist.api)
    implementation(projects.data.watchlist.implementation)
    implementation(projects.data.popularshows.api)
    implementation(projects.data.popularshows.implementation)
    implementation(projects.data.recommendedshows.api)
    implementation(projects.data.recommendedshows.implementation)
    implementation(projects.data.requestManager.api)
    implementation(projects.data.requestManager.api)
    implementation(projects.data.requestManager.implementation)
    implementation(projects.data.search.api)
    implementation(projects.data.search.implementation)
    implementation(projects.data.seasondetails.api)
    implementation(projects.data.seasondetails.implementation)
    implementation(projects.data.seasons.api)
    implementation(projects.data.seasons.implementation)
    implementation(projects.data.showdetails.api)
    implementation(projects.data.showdetails.implementation)
    implementation(projects.data.shows.api)
    implementation(projects.data.shows.implementation)
    implementation(projects.data.similar.api)
    implementation(projects.data.similar.implementation)
    implementation(projects.data.topratedshows.api)
    implementation(projects.data.topratedshows.implementation)
    implementation(projects.data.trailers.api)
    implementation(projects.data.trailers.implementation)
    implementation(projects.data.trendingshows.api)
    implementation(projects.data.trendingshows.implementation)
    implementation(projects.data.upcomingshows.api)
    implementation(projects.data.upcomingshows.implementation)
    implementation(projects.data.watchproviders.api)
    implementation(projects.data.watchproviders.implementation)
    implementation(projects.data.database.sqldelight)
    implementation(projects.data.datastore.api)
    implementation(projects.data.datastore.implementation)
    implementation(projects.domain.discover)
    implementation(projects.domain.genre)
    implementation(projects.domain.recommendedshows)
    implementation(projects.domain.seasondetails)
    implementation(projects.domain.showdetails)
    implementation(projects.domain.similarshows)
    implementation(projects.domain.watchproviders)
    implementation(projects.domain.watchlist)
    implementation(projects.presenter.discover)
    implementation(projects.presenter.watchlist)
    implementation(projects.presenter.home)
    implementation(projects.presenter.moreShows)
    implementation(projects.presenter.search)
    implementation(projects.presenter.seasondetails)
    implementation(projects.presenter.settings)
    implementation(projects.presenter.showDetails)
    implementation(projects.presenter.trailers)

    implementation(libs.androidx.compose.activity)
    implementation(libs.androidx.core.core)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.material3)
    implementation(libs.appauth)

    implementation(libs.decompose.decompose)
    implementation(libs.decompose.extensions.compose)
    implementation(libs.bundles.kotlinInject)

    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.savedstate)
    implementation(libs.sqldelight.runtime)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.coroutines.core)
    implementation(libs.ktor.core)

    baselineProfile(projects.benchmark)

    runtimeOnly(libs.androidx.profileinstaller)
}
