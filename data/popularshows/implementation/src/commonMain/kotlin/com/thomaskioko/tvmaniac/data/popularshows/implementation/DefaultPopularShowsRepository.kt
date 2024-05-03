package com.thomaskioko.tvmaniac.data.popularshows.implementation

import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.thomaskioko.tvmaniac.core.base.model.AppCoroutineDispatchers
import com.thomaskioko.tvmaniac.core.networkutil.mapResult
import com.thomaskioko.tvmaniac.core.networkutil.model.Either
import com.thomaskioko.tvmaniac.core.networkutil.model.Failure
import com.thomaskioko.tvmaniac.core.paging.CommonPagingConfig.pagingConfig
import com.thomaskioko.tvmaniac.core.paging.PaginatedRemoteMediator
import com.thomaskioko.tvmaniac.data.popularshows.api.PopularShowsDao
import com.thomaskioko.tvmaniac.data.popularshows.api.PopularShowsRepository
import com.thomaskioko.tvmaniac.resourcemanager.api.RequestManagerRepository
import com.thomaskioko.tvmaniac.resourcemanager.api.RequestTypeConfig.POPULAR_SHOWS
import com.thomaskioko.tvmaniac.shows.api.ShowEntity
import com.thomaskioko.tvmaniac.tmdb.api.DEFAULT_API_PAGE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import me.tatarka.inject.annotations.Inject
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.impl.extensions.fresh
import org.mobilenativefoundation.store.store5.impl.extensions.get

@Inject
class DefaultPopularShowsRepository(
  private val store: PopularShowsStore,
  private val popularShowsDao: PopularShowsDao,
  private val requestManagerRepository: RequestManagerRepository,
  private val dispatchers: AppCoroutineDispatchers,
) : PopularShowsRepository {

  override suspend fun observePopularShows(
    forceRefresh: Boolean,
  ): Flow<Either<Failure, List<ShowEntity>>> {
    val refresh =
      forceRefresh ||
        requestManagerRepository.isRequestExpired(
          entityId = DEFAULT_API_PAGE,
          requestType = POPULAR_SHOWS.name,
          threshold = POPULAR_SHOWS.duration,
        )
    return store
      .stream(
        StoreReadRequest.cached(
          key = DEFAULT_API_PAGE,
          refresh = refresh,
        ),
      )
      .mapResult(getShows())
      .flowOn(dispatchers.io)
  }

  override fun getPagedPopularShows(): Flow<PagingData<ShowEntity>> {
    return Pager(
        config = pagingConfig,
        remoteMediator =
          PaginatedRemoteMediator(
            getLastPage = popularShowsDao::getLastPage,
            deleteLocalEntity = store::clear,
            fetch = store::fresh,
          ),
        pagingSourceFactory = popularShowsDao::getPagedPopularShows,
      )
      .flow
  }

  private suspend fun getShows(): List<ShowEntity> = store.get(key = DEFAULT_API_PAGE)
}
