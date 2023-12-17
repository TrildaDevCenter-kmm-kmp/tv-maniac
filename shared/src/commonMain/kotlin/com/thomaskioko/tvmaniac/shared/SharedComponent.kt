package com.thomaskioko.tvmaniac.shared

import com.thomaskioko.trakt.service.implementation.inject.TraktComponent
import com.thomaskioko.tvmaniac.data.cast.implementation.CastComponent
import com.thomaskioko.tvmaniac.data.popularshows.implementation.PopularShowsComponent
import com.thomaskioko.tvmaniac.data.showdetails.implementation.ShowDetailsComponent
import com.thomaskioko.tvmaniac.data.trailers.implementation.TrailerComponent
import com.thomaskioko.tvmaniac.data.upcomingshows.implementation.UpcomingShowsComponent
import com.thomaskioko.tvmaniac.datastore.implementation.DataStoreComponent
import com.thomaskioko.tvmaniac.db.DatabaseComponent
import com.thomaskioko.tvmaniac.discover.implementation.TrendingShowsComponent
import com.thomaskioko.tvmaniac.episodes.implementation.EpisodeComponent
import com.thomaskioko.tvmaniac.resourcemanager.implementation.RequestManagerComponent
import com.thomaskioko.tvmaniac.seasondetails.implementation.SeasonDetailsComponent
import com.thomaskioko.tvmaniac.seasons.implementation.SeasonsComponent
import com.thomaskioko.tvmaniac.shows.implementation.DiscoverComponent
import com.thomaskioko.tvmaniac.similar.implementation.SimilarShowsComponent
import com.thomaskioko.tvmaniac.tmdb.implementation.TmdbComponent
import com.thomaskioko.tvmaniac.toprated.data.implementation.TopRatedShowsComponent
import com.thomaskioko.tvmaniac.traktauth.implementation.TraktAuthenticationComponent
import com.thomaskioko.tvmaniac.util.inject.LoggingComponent
import com.thomaskioko.tvmaniac.util.inject.UtilPlatformComponent
import com.thomaskioko.tvmaniac.watchlist.implementation.LibraryComponent

abstract class SharedComponent :
    CastComponent,
    DataStoreComponent,
    DatabaseComponent,
    DiscoverComponent,
    EpisodeComponent,
    LibraryComponent,
    LoggingComponent,
    PopularShowsComponent,
    RequestManagerComponent,
    SeasonDetailsComponent,
    SeasonsComponent,
    ShowDetailsComponent,
    SimilarShowsComponent,
    TmdbComponent,
    TopRatedShowsComponent,
    TrailerComponent,
    TraktAuthenticationComponent,
    TraktComponent,
    TrendingShowsComponent,
    UtilPlatformComponent,
    UpcomingShowsComponent
