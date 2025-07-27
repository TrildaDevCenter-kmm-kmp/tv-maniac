package com.thomaskioko.tvmaniac.presenter.showdetails

import com.arkivanov.decompose.ComponentContext
import com.thomaskioko.tvmaniac.core.base.annotations.ActivityScope
import com.thomaskioko.tvmaniac.core.base.extensions.coroutineScope
import com.thomaskioko.tvmaniac.core.logger.Logger
import com.thomaskioko.tvmaniac.core.view.ObservableLoadingCounter
import com.thomaskioko.tvmaniac.core.view.UiMessageManager
import com.thomaskioko.tvmaniac.core.view.collectStatus
import com.thomaskioko.tvmaniac.domain.recommendedshows.RecommendedShowsInteractor
import com.thomaskioko.tvmaniac.domain.showdetails.ObservableShowDetailsInteractor
import com.thomaskioko.tvmaniac.domain.showdetails.ShowDetailsInteractor
import com.thomaskioko.tvmaniac.domain.similarshows.SimilarShowsInteractor
import com.thomaskioko.tvmaniac.domain.watchproviders.WatchProvidersInteractor
import com.thomaskioko.tvmaniac.presenter.showdetails.model.ShowSeasonDetailsParam
import com.thomaskioko.tvmaniac.shows.api.WatchlistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Inject
@ContributesBinding(ActivityScope::class, boundType = ShowDetailsPresenter::class)
class DefaultShowDetailsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val showId: Long,
    @Assisted private val onBack: () -> Unit,
    @Assisted private val onNavigateToShow: (id: Long) -> Unit,
    @Assisted private val onNavigateToSeason: (param: ShowSeasonDetailsParam) -> Unit,
    @Assisted private val onNavigateToTrailer: (id: Long) -> Unit,
    private val watchlistRepository: WatchlistRepository,
    private val recommendedShowsInteractor: RecommendedShowsInteractor,
    private val showDetailsInteractor: ShowDetailsInteractor,
    private val similarShowsInteractor: SimilarShowsInteractor,
    private val watchProvidersInteractor: WatchProvidersInteractor,
    observableShowDetailsInteractor: ObservableShowDetailsInteractor,
    private val logger: Logger,
) : ShowDetailsPresenter, ComponentContext by componentContext {

    private val recommendedShowsLoadingState = ObservableLoadingCounter()
    private val showDetailsLoadingState = ObservableLoadingCounter()
    private val similarShowsLoadingState = ObservableLoadingCounter()
    private val watchProvidersLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    private val coroutineScope = coroutineScope()
    private val _state = MutableStateFlow(ShowDetailsContent.Empty)

    init {
        observableShowDetailsInteractor(showId)

        coroutineScope.launch { observeShowDetails() }
    }

    override val state: StateFlow<ShowDetailsContent> = combine(
        recommendedShowsLoadingState.observable,
        showDetailsLoadingState.observable,
        similarShowsLoadingState.observable,
        watchProvidersLoadingState.observable,
        observableShowDetailsInteractor.flow,
    ) { recommendedShowsUpdating, showDetailsUpdating, similarShowsUpdating, watchProvidersUpdating, showDetails ->
        ShowDetailsContent(
            showDetails = showDetails.toShowDetails(),
            recommendedShowsRefreshing = recommendedShowsUpdating,
            showDetailsRefreshing = showDetailsUpdating,
            similarShowsRefreshing = similarShowsUpdating,
            watchProvidersRefreshing = watchProvidersUpdating,
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _state.value,
    )

    override fun dispatch(action: ShowDetailsAction) {
        when (action) {
            is SeasonClicked -> {
                _state.update {
                    it.copy(selectedSeasonIndex = action.params.selectedSeasonIndex)
                }
                onNavigateToSeason(action.params)
            }

            is DetailShowClicked -> onNavigateToShow(action.id)
            is WatchTrailerClicked -> onNavigateToTrailer(action.id)
            is FollowShowClicked -> {
                coroutineScope.launch {
                    watchlistRepository.updateLibrary(
                        id = showId,
                        addToLibrary = !action.addToLibrary,
                    )
                }
            }

            DetailBackClicked -> onBack()
            ReloadShowDetails -> coroutineScope.launch { observeShowDetails(forceReload = true) }
            DismissErrorSnackbar -> coroutineScope.launch { _state.update { it.copy(message = null) } }
            DismissShowsListSheet -> coroutineScope.launch { _state.update { it.copy(showListSheet = false) } }
            ShowShowsListSheet -> coroutineScope.launch { _state.update { it.copy(showListSheet = true) } }
            CreateCustomList -> {
                // TODO:: Add implementation
            }
        }
    }

    private fun observeShowDetails(forceReload: Boolean = false) {
        coroutineScope.launch {
            recommendedShowsInteractor(RecommendedShowsInteractor.Param(showId, forceReload))
                .collectStatus(recommendedShowsLoadingState, logger, uiMessageManager)
        }

        coroutineScope.launch {
            showDetailsInteractor(ShowDetailsInteractor.Param(showId, forceReload))
                .collectStatus(showDetailsLoadingState, logger, uiMessageManager)
        }

        coroutineScope.launch {
            similarShowsInteractor(SimilarShowsInteractor.Param(showId, forceReload))
                .collectStatus(similarShowsLoadingState, logger, uiMessageManager)
        }

        coroutineScope.launch {
            watchProvidersInteractor(WatchProvidersInteractor.Param(showId, forceReload))
                .collectStatus(watchProvidersLoadingState, logger, uiMessageManager)
        }
    }
}

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class, ShowDetailsPresenter.Factory::class)
class DefaultShowDetailsPresenterFactory(
    private val presenter: (
        componentContext: ComponentContext,
        id: Long,
        onBack: () -> Unit,
        onNavigateToShow: (id: Long) -> Unit,
        onNavigateToSeason: (param: ShowSeasonDetailsParam) -> Unit,
        onNavigateToTrailer: (id: Long) -> Unit,
    ) -> ShowDetailsPresenter,
) : ShowDetailsPresenter.Factory {
    override fun invoke(
        componentContext: ComponentContext,
        id: Long,
        onBack: () -> Unit,
        onNavigateToShow: (id: Long) -> Unit,
        onNavigateToSeason: (param: ShowSeasonDetailsParam) -> Unit,
        onNavigateToTrailer: (id: Long) -> Unit,
    ): ShowDetailsPresenter = presenter(
        componentContext,
        id,
        onBack,
        onNavigateToShow,
        onNavigateToSeason,
        onNavigateToTrailer,
    )
}
