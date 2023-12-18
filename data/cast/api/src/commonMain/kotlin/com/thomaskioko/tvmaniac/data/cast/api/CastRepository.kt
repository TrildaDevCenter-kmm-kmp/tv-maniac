package com.thomaskioko.tvmaniac.data.cast.api

import com.thomaskioko.tvmaniac.core.db.SeasonCast
import com.thomaskioko.tvmaniac.core.db.ShowCast
import kotlinx.coroutines.flow.Flow

interface CastRepository {
    fun fetchSeasonCast(seasonId: Long): List<SeasonCast>
    fun observeSeasonCast(seasonId: Long): Flow<List<SeasonCast>>
    fun fetchShowCast(showId: Long): List<ShowCast>
    fun observeShowCast(showId: Long): Flow<List<ShowCast>>
}