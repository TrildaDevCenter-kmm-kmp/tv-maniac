package com.thomaskioko.tvmaniac.seasondetails.presenter

import com.arkivanov.decompose.ComponentContext
import com.thomaskioko.tvmaniac.core.base.annotations.ActivityScope
import com.thomaskioko.tvmaniac.core.base.extensions.coroutineScope
import com.thomaskioko.tvmaniac.core.logger.Logger
import com.thomaskioko.tvmaniac.core.view.ObservableLoadingCounter
import com.thomaskioko.tvmaniac.core.view.UiMessageManager
import com.thomaskioko.tvmaniac.core.view.collectStatus
import com.thomaskioko.tvmaniac.domain.seasondetails.ObservableSeasonDetailsInteractor
import com.thomaskioko.tvmaniac.domain.seasondetails.SeasonDetailsInteractor
import com.thomaskioko.tvmaniac.seasondetails.api.SeasonDetailsParam
import com.thomaskioko.tvmaniac.seasondetails.presenter.model.SeasonDetailsUiParam
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
@ContributesBinding(ActivityScope::class, boundType = SeasonDetailsPresenter::class)
class DefaultSeasonDetailsPresenter(
    @Assisted componentContext: ComponentContext,
    @Assisted private val param: SeasonDetailsUiParam,
    @Assisted private val onBack: () -> Unit,
    @Assisted private val onEpisodeClick: (id: Long) -> Unit,
    observableSeasonDetailsInteractor: ObservableSeasonDetailsInteractor,
    private val seasonDetailsInteractor: SeasonDetailsInteractor,
    private val logger: Logger,
) : SeasonDetailsPresenter, ComponentContext by componentContext {

    private val seasonDetailsParam: SeasonDetailsParam = SeasonDetailsParam(
        showId = param.showId,
        seasonId = param.seasonId,
        seasonNumber = param.seasonNumber,
    )
    private val seasonDetailsLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()
    private val coroutineScope = coroutineScope()
    private val _state: MutableStateFlow<SeasonDetailsModel> = MutableStateFlow(SeasonDetailsModel.Empty)

    override val state: StateFlow<SeasonDetailsModel> = combine(
        seasonDetailsLoadingState.observable,
        observableSeasonDetailsInteractor.flow,
        _state,
    ) { seasonDetailsUpdating, detailsResult, currentState ->
        currentState.copy(
            isUpdating = seasonDetailsUpdating,
            seasonId = detailsResult.seasonDetails.seasonId,
            seasonName = detailsResult.seasonDetails.name,
            seasonOverview = detailsResult.seasonDetails.seasonOverview,
            episodeCount = detailsResult.seasonDetails.episodeCount,
            imageUrl = detailsResult.seasonDetails.imageUrl,
            episodeDetailsList = detailsResult.seasonDetails.episodes.toEpisodes(),
            seasonImages = detailsResult.images.toImageList(),
            seasonCast = detailsResult.cast.toCastList(),
        )
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SeasonDetailsModel.Empty,
    )

    init {
        observableSeasonDetailsInteractor(seasonDetailsParam)
        observeSeasonDetails()
    }

    override fun dispatch(action: SeasonDetailsAction) {
        coroutineScope.launch {
            when (action) {
                is EpisodeClicked -> onEpisodeClick(action.id)
                is UpdateEpisodeStatus -> updateState { copy(showGalleryBottomSheet = false) }
                SeasonDetailsBackClicked -> onBack()
                ReloadSeasonDetails -> observeSeasonDetails()
                DismissSeasonDialog -> updateState { copy(showSeasonWatchStateDialog = !showSeasonWatchStateDialog) }
                DismissSeasonGallery -> updateState { copy(showGalleryBottomSheet = false) }
                OnEpisodeHeaderClicked -> updateState { copy(expandEpisodeItems = !expandEpisodeItems) }
                SeasonGalleryClicked -> updateState { copy(showGalleryBottomSheet = !showGalleryBottomSheet) }
                ShowMarkSeasonDialog -> updateState { copy(showSeasonWatchStateDialog = true) }
                UpdateSeasonWatchedState -> updateState { copy(showSeasonWatchStateDialog = false) }
            }
        }
    }

    private fun updateState(update: SeasonDetailsModel.() -> SeasonDetailsModel) {
        _state.update { it.update() }
    }

    private fun observeSeasonDetails(forceReload: Boolean = false) {
        coroutineScope.launch {
            seasonDetailsInteractor(SeasonDetailsInteractor.Param(seasonDetailsParam, forceReload))
                .collectStatus(seasonDetailsLoadingState, logger, uiMessageManager)
        }
    }
}

@Inject
@SingleIn(ActivityScope::class)
@ContributesBinding(ActivityScope::class, SeasonDetailsPresenter.Factory::class)
class DefaultSeasonDetailsPresenterFactory(
    private val presenter: (
        componentContext: ComponentContext,
        param: SeasonDetailsUiParam,
        onBack: () -> Unit,
        onNavigateToEpisodeDetails: (id: Long) -> Unit,
    ) -> SeasonDetailsPresenter,
) : SeasonDetailsPresenter.Factory {
    override fun invoke(
        componentContext: ComponentContext,
        param: SeasonDetailsUiParam,
        onBack: () -> Unit,
        onNavigateToEpisodeDetails: (id: Long) -> Unit,
    ): SeasonDetailsPresenter = presenter(componentContext, param, onBack, onNavigateToEpisodeDetails)
}
