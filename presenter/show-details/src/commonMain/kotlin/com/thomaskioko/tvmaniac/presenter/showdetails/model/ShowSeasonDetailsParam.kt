package com.thomaskioko.tvmaniac.presenter.showdetails.model

import kotlinx.serialization.Serializable

@Serializable
data class ShowSeasonDetailsParam(
    val showId: Long,
    val seasonId: Long,
    val seasonNumber: Long,
    val selectedSeasonIndex: Int,
)
