package com.thomaskioko.tvmaniac.episodeimages.api

import com.thomaskioko.tvmaniac.util.model.Either
import com.thomaskioko.tvmaniac.util.model.Failure
import kotlinx.coroutines.flow.Flow

interface EpisodeImageRepository {

    fun updateEpisodeImage(traktId: Long): Flow<Either<Failure, Unit>>
}
