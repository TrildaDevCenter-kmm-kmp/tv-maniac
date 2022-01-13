package com.thomaskioko.tvmaniac.interactors

import com.thomaskioko.tvmaniac.discover.api.model.ShowUiModel
import com.thomaskioko.tvmaniac.discover.api.repository.TvShowsRepository
import com.thomaskioko.tvmaniac.shared.core.FlowInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetShowInteractor constructor(
    private val repository: TvShowsRepository,
) : FlowInteractor<Int, ShowUiModel>() {

    override fun run(params: Int): Flow<ShowUiModel> = repository.observeShow(params)
        .map { it.data?.toTvShow() ?: ShowUiModel.EMPTY_SHOW }
}