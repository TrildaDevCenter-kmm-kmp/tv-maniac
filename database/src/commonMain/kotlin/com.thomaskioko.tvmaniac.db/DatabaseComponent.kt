package com.thomaskioko.tvmaniac.db

import app.cash.sqldelight.db.SqlDriver
import com.thomaskioko.tvmaniac.core.db.Casts
import com.thomaskioko.tvmaniac.core.db.Episode
import com.thomaskioko.tvmaniac.core.db.Episode_image
import com.thomaskioko.tvmaniac.core.db.Featured_shows
import com.thomaskioko.tvmaniac.core.db.Genres
import com.thomaskioko.tvmaniac.core.db.Last_requests
import com.thomaskioko.tvmaniac.core.db.Library
import com.thomaskioko.tvmaniac.core.db.Popular_shows
import com.thomaskioko.tvmaniac.core.db.Recommended_shows
import com.thomaskioko.tvmaniac.core.db.Season
import com.thomaskioko.tvmaniac.core.db.Season_images
import com.thomaskioko.tvmaniac.core.db.Season_videos
import com.thomaskioko.tvmaniac.core.db.Similar_shows
import com.thomaskioko.tvmaniac.core.db.Toprated_shows
import com.thomaskioko.tvmaniac.core.db.Trailers
import com.thomaskioko.tvmaniac.core.db.Trending_shows
import com.thomaskioko.tvmaniac.core.db.TvManiacDatabase
import com.thomaskioko.tvmaniac.core.db.Tvshows
import com.thomaskioko.tvmaniac.core.db.Upcoming_shows
import com.thomaskioko.tvmaniac.core.db.Watch_providers
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo


@ContributesTo(AppScope::class)
interface DatabaseComponent {

  @Provides
  fun provideTvManiacDatabase(sqlDriver: SqlDriver): TvManiacDatabase =
    TvManiacDatabase(
      driver = sqlDriver,
      last_requestsAdapter =
        Last_requests.Adapter(
          timestampAdapter = InstantColumnAdapter,
        ),
      episode_imageAdapter =
        Episode_image.Adapter(
          idAdapter = IdAdapter(),
          tmdb_idAdapter = IdAdapter(),
        ),
      episodeAdapter =
        Episode.Adapter(
          idAdapter = IdAdapter(),
          season_idAdapter = IdAdapter(),
          show_idAdapter = IdAdapter(),
        ),
      seasonAdapter =
        Season.Adapter(
          idAdapter = IdAdapter(),
          show_idAdapter = IdAdapter(),
        ),
      similar_showsAdapter =
        Similar_shows.Adapter(
          idAdapter = IdAdapter(),
          similar_show_idAdapter = IdAdapter(),
        ),
      trailersAdapter =
        Trailers.Adapter(
          show_idAdapter = IdAdapter(),
        ),
      libraryAdapter =
        Library.Adapter(
          idAdapter = IdAdapter(),
        ),
      trending_showsAdapter =
        Trending_shows.Adapter(
          idAdapter = IdAdapter(),
          pageAdapter = IdAdapter(),
        ),
      tvshowsAdapter =
        Tvshows.Adapter(
          idAdapter = IdAdapter(),
          genre_idsAdapter = intColumnAdapter,
        ),
      upcoming_showsAdapter =
        Upcoming_shows.Adapter(
          idAdapter = IdAdapter(),
          pageAdapter = IdAdapter(),
        ),
      toprated_showsAdapter =
        Toprated_shows.Adapter(
          idAdapter = IdAdapter(),
          pageAdapter = IdAdapter(),
        ),
      popular_showsAdapter =
        Popular_shows.Adapter(
          idAdapter = IdAdapter(),
          pageAdapter = IdAdapter(),
        ),
      genresAdapter =
        Genres.Adapter(
          idAdapter = IdAdapter(),
          tmdb_idAdapter = IdAdapter(),
        ),
      season_imagesAdapter =
        Season_images.Adapter(
          season_idAdapter = IdAdapter(),
        ),
      season_videosAdapter =
        Season_videos.Adapter(
          season_idAdapter = IdAdapter(),
        ),
      recommended_showsAdapter =
        Recommended_shows.Adapter(
          idAdapter = IdAdapter(),
          recommended_show_idAdapter = IdAdapter(),
        ),
      castsAdapter =
        Casts.Adapter(
          idAdapter = IdAdapter(),
          season_idAdapter = IdAdapter(),
          tmdb_idAdapter = IdAdapter(),
        ),
      watch_providersAdapter =
        Watch_providers.Adapter(
          idAdapter = IdAdapter(),
          tmdb_idAdapter = IdAdapter(),
        ),
      featured_showsAdapter =
        Featured_shows.Adapter(
          idAdapter = IdAdapter(),
        ),
    )
}
