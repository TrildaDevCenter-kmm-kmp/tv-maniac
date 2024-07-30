package com.thomaskioko.tvmaniac.data.recommendedshows.implementation

import com.thomaskioko.tvmaniac.core.base.model.AppCoroutineDispatchers
import com.thomaskioko.tvmaniac.core.db.RecommendedShows
import com.thomaskioko.tvmaniac.core.networkutil.mapResult
import com.thomaskioko.tvmaniac.core.networkutil.model.Either
import com.thomaskioko.tvmaniac.core.networkutil.model.Failure
import com.thomaskioko.tvmaniac.data.recommendedshows.api.RecommendedShowsParams
import com.thomaskioko.tvmaniac.data.recommendedshows.api.RecommendedShowsRepository
import com.thomaskioko.tvmaniac.resourcemanager.api.RequestManagerRepository
import com.thomaskioko.tvmaniac.resourcemanager.api.RequestTypeConfig.RECOMMENDED_SHOWS
import com.thomaskioko.tvmaniac.tmdb.api.DEFAULT_API_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.impl.extensions.get

@Inject
class DefaultRecommendedShowsRepository(
  private val store: RecommendedShowStore,
  private val requestManagerRepository: RequestManagerRepository,
  private val dispatchers: AppCoroutineDispatchers,
) : RecommendedShowsRepository {

  override fun observeRecommendedShows(
    id: Long,
    forceReload: Boolean
  ): Flow<Either<Failure, List<RecommendedShows>>> {
    return store
      .stream(
        StoreReadRequest.cached(
          key = RecommendedShowsParams(showId = id, page = DEFAULT_API_PAGE),
          refresh =
            forceReload ||
              requestManagerRepository.isRequestExpired(
                entityId = id,
                requestType = RECOMMENDED_SHOWS.name,
                threshold = RECOMMENDED_SHOWS.duration,
              ),
        ),
      )
      .mapResult()
      .flowOn(dispatchers.io)
  }
}
