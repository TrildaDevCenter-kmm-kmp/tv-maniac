package com.thomaskioko.tvmaniac.similar.testing

import com.thomaskioko.tvmaniac.core.db.SimilarShows
import com.thomaskioko.tvmaniac.similar.api.SimilarShowsRepository
import com.thomaskioko.tvmaniac.util.model.Either
import com.thomaskioko.tvmaniac.util.model.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeSimilarShowsRepository : SimilarShowsRepository {

    private var similarShowsResult: Flow<Either<Failure, List<SimilarShows>>> = flowOf()

    suspend fun setSimilarShowsResult(result: Either<Failure, List<SimilarShows>>) {
        similarShowsResult = flow { emit(result) }
    }

    override suspend fun fetchSimilarShows(traktId: Long): List<SimilarShows> = emptyList()

    override fun observeSimilarShows(traktId: Long): Flow<Either<Failure, List<SimilarShows>>> =
        similarShowsResult
}