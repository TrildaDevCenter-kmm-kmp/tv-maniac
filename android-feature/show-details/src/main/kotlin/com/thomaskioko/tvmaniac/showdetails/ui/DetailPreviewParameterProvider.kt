package com.thomaskioko.tvmaniac.showdetails.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.thomaskioko.tvmaniac.core.view.UiMessage
import com.thomaskioko.tvmaniac.presenter.showdetails.ShowDetailsContent
import com.thomaskioko.tvmaniac.presenter.showdetails.model.CastModel
import com.thomaskioko.tvmaniac.presenter.showdetails.model.ProviderModel
import com.thomaskioko.tvmaniac.presenter.showdetails.model.SeasonModel
import com.thomaskioko.tvmaniac.presenter.showdetails.model.ShowDetailsModel
import com.thomaskioko.tvmaniac.presenter.showdetails.model.ShowModel
import com.thomaskioko.tvmaniac.presenter.showdetails.model.TrailerModel
import kotlinx.collections.immutable.persistentListOf

val showDetailsContent = ShowDetailsContent(
    showDetails = ShowDetailsModel(
        tmdbId = 849583,
        title = "Loki",
        overview = "After stealing the Tesseract during the events of “Avengers: Endgame,” " +
            "an alternate version of Loki is brought to the mysterious Time Variance " +
            "Authority, a bureaucratic organization that exists outside of time and " +
            "space and monitors the timeline. They give Loki a choice: face being " +
            "erased from existence due to being a “time variant”or help fix " +
            "the timeline and stop a greater threat.",
        posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
        language = "en",
        votes = 4958,
        rating = 8.1,
        genres = persistentListOf("Horror", "Action"),
        status = "Returning Series",
        year = "2024",
        seasonsList = persistentListOf(
            SeasonModel(
                seasonId = 114355,
                tvShowId = 84958,
                name = "Season 1",
                seasonNumber = 1,
            ),
        ),
        trailersList = persistentListOf(
            TrailerModel(
                showId = 1232,
                key = "",
                name = "",
                youtubeThumbnailUrl = "",
            ),
            TrailerModel(
                showId = 1232,
                key = "",
                name = "",
                youtubeThumbnailUrl = "",
            ),
        ),
        similarShows = persistentListOf(
            ShowModel(
                tmdbId = 1232,
                title = "Loki",
                posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
                backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
                isInLibrary = false,
            ),
        ),
        recommendedShows = persistentListOf(
            ShowModel(
                tmdbId = 1232,
                title = "Loki",
                posterImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
                backdropImageUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg",
                isInLibrary = false,
            ),
        ),
        providers = persistentListOf(
            ProviderModel(id = 1L, logoUrl = "/kEl2t3OhXc3Zb9FBh1AuYzRTgZp.jpg", name = "Netflix"),
        ),
        castsList = persistentListOf(
            CastModel(id = 1L, name = "Character", profileUrl = null, characterName = "Starring"),
        ),
        hasWebViewInstalled = false,
        isInLibrary = false,
    ),
    message = null,
)

val showDetailsContentWithEmptyInfo = showDetailsContent.copy(showDetails = ShowDetailsModel.Empty)

val showDetailsContentWithError = showDetailsContent.copy(
    message = UiMessage(
        message = "Opps! Something went wrong",
    ),
)

class DetailPreviewParameterProvider : PreviewParameterProvider<ShowDetailsContent> {
    override val values: Sequence<ShowDetailsContent>
        get() {
            return sequenceOf(
                showDetailsContent,
                showDetailsContentWithEmptyInfo,
                showDetailsContentWithError,
            )
        }
}
