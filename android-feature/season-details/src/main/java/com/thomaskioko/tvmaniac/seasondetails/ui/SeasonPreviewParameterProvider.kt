package com.thomaskioko.tvmaniac.seasondetails.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.thomaskioko.tvmaniac.core.view.UiMessage
import com.thomaskioko.tvmaniac.seasondetails.presenter.SeasonDetailsModel
import com.thomaskioko.tvmaniac.seasondetails.presenter.model.EpisodeDetailsModel
import com.thomaskioko.tvmaniac.seasondetails.presenter.model.SeasonImagesModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

val episodeDetailsModel = EpisodeDetailsModel(
    id = 2534997,
    episodeNumberTitle = "E01 • Glorious Purpose",
    overview = "After stealing the Tesseract in Avengers: Endgame, Loki lands before the Time Variance Authority.",
    voteCount = 42,
    runtime = 21,
    seasonId = 4654,
    imageUrl = "",
    episodeNumber = "01",
    episodeTitle = "Glorious Purpose",
    seasonEpisodeNumber = "S01 | E01",
)

val seasonDetailsLoaded = SeasonDetailsModel(
    seasonId = 1,
    seasonName = "Specials",
    episodeCount = 8,
    watchProgress = 0.4f,
    imageUrl = "https://image.tmdb.org/t/p/w500/path/to/image.jpg",
    episodeDetailsList = List(8) { episodeDetailsModel }.toPersistentList(),
    seasonImages = persistentListOf(
        SeasonImagesModel(
            id = 1L,
            imageUrl = null,
        ),
        SeasonImagesModel(
            id = 1L,
            imageUrl = null,
        ),
    ),
    seasonOverview = "After stealing the Tesseract in Avengers: Endgame, Loki lands before the " +
        "Time Variance Authority.",
    isSeasonWatched = false,
    seasonCast = persistentListOf(),
)

class SeasonPreviewParameterProvider : PreviewParameterProvider<SeasonDetailsModel> {
    override val values: Sequence<SeasonDetailsModel>
        get() {
            return sequenceOf(
                seasonDetailsLoaded,
                seasonDetailsLoaded.copy(message = UiMessage("Opps! Something went wrong")),
            )
        }
}
