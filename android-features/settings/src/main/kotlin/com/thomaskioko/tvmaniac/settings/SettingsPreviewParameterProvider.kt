package com.thomaskioko.tvmaniac.settings

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.thomaskioko.tvmaniac.datastore.api.Theme
import com.thomaskioko.tvmaniac.presentation.settings.SettingsContent
import com.thomaskioko.tvmaniac.presentation.settings.SettingsState

class SettingsPreviewParameterProvider : PreviewParameterProvider<SettingsState> {
    override val values: Sequence<SettingsState>
        get() {
            return sequenceOf(
                SettingsContent(
                    theme = Theme.DARK,
                    showPopup = false,
                    loggedIn = false,
                    showTraktDialog = false,
                    traktUserName = "@j_Doe",
                    traktFullName = "J Doe",
                    traktUserPicUrl = "image.png",
                    errorMessage = null,
                ),
                SettingsContent(
                    theme = Theme.DARK,
                    showPopup = false,
                    loggedIn = true,
                    showTraktDialog = false,
                    traktUserName = "@j_Doe",
                    traktFullName = "J Doe",
                    traktUserPicUrl = "image.png",
                    errorMessage = null,
                ),
            )
        }
}
