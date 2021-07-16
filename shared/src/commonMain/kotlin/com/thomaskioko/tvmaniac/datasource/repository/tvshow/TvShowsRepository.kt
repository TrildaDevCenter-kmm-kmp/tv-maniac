package com.thomaskioko.tvmaniac.datasource.repository.tvshow

import com.thomaskioko.tvmaniac.datasource.cache.model.TvShowsEntity

interface TvShowsRepository  {

    suspend fun getPopularTvShows(page: Int) : List<TvShowsEntity>

    suspend fun getTopRatedTvShows(page: Int) : List<TvShowsEntity>
}