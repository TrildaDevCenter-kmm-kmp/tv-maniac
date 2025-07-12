package com.thomaskioko.tvmaniac.presenter.trailers

sealed interface TrailersAction

data object ReloadTrailers : TrailersAction

data class TrailerSelected(
    val trailerKey: String,
) : TrailersAction

data class VideoPlayerError(
    val errorMessage: String,
) : TrailersAction
