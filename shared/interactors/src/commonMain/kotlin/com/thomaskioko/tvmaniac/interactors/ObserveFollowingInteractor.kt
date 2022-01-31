package com.thomaskioko.tvmaniac.interactors

import com.thomaskioko.tvmaniac.datasource.cache.Show
import com.thomaskioko.tvmaniac.discover.api.model.TvShow
import com.thomaskioko.tvmaniac.discover.api.repository.TvShowsRepository
import com.thomaskioko.tvmaniac.shared.core.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveFollowingInteractor constructor(
    private val repository: TvShowsRepository,
) : FlowInteractor<Unit, List<TvShow>>() {

    override fun run(params: Unit): Flow<List<TvShow>> = repository.observeFollowing()
        .map { it.toTvShowList() }
}

fun List<Show>.toTvShowList(): List<TvShow> {
    return map {
        TvShow(
            id = it.id,
            title = it.title,
            overview = it.description,
            language = it.language,
            posterImageUrl = it.poster_image_url,
            backdropImageUrl = it.backdrop_image_url,
            votes = it.votes.toInt(),
            averageVotes = it.vote_average,
            genreIds = it.genre_ids,
            year = it.year,
            status = it.status,
            following = it.following
        )
    }
}