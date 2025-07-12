package com.thomaskioko.tvmaniac.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.thomaskioko.tvmaniac.compose.components.TvManiacBottomNavigationItem
import com.thomaskioko.tvmaniac.compose.components.TvManiacNavigationBar
import com.thomaskioko.tvmaniac.discover.ui.DiscoverScreen
import com.thomaskioko.tvmaniac.i18n.MR.strings.menu_item_discover
import com.thomaskioko.tvmaniac.i18n.MR.strings.menu_item_library
import com.thomaskioko.tvmaniac.i18n.MR.strings.menu_item_search
import com.thomaskioko.tvmaniac.i18n.MR.strings.menu_item_settings
import com.thomaskioko.tvmaniac.i18n.resolve
import com.thomaskioko.tvmaniac.presenter.home.HomePresenter
import com.thomaskioko.tvmaniac.presenter.home.HomePresenter.Child.Discover
import com.thomaskioko.tvmaniac.presenter.home.HomePresenter.Child.Search
import com.thomaskioko.tvmaniac.presenter.home.HomePresenter.Child.Settings
import com.thomaskioko.tvmaniac.presenter.home.HomePresenter.Child.Watchlist
import com.thomaskioko.tvmaniac.search.ui.SearchScreen
import com.thomaskioko.tvmaniac.settings.ui.SettingsScreen
import com.thomaskioko.tvmaniac.ui.library.WatchlistScreen

@Composable
fun HomeScreen(
    presenter: HomePresenter,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ChildrenContent(homePresenter = presenter, modifier = Modifier.weight(1F))
        BottomNavigationContent(component = presenter, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun ChildrenContent(homePresenter: HomePresenter, modifier: Modifier = Modifier) {
    val childStack by homePresenter.homeChildStack.collectAsState()

    Children(
        modifier = modifier,
        stack = childStack,
    ) { child ->
        val fillMaxSizeModifier = Modifier.fillMaxSize()
        when (val screen = child.instance) {
            is Discover -> {
                DiscoverScreen(
                    presenter = screen.presenter,
                    modifier = fillMaxSizeModifier,
                )
            }
            is Watchlist -> {
                WatchlistScreen(
                    presenter = screen.presenter,
                    modifier = fillMaxSizeModifier,
                )
            }
            is Search -> {
                SearchScreen(
                    presenter = screen.presenter,
                    modifier = fillMaxSizeModifier,
                )
            }
            is Settings -> {
                SettingsScreen(
                    presenter = screen.presenter,
                    modifier = fillMaxSizeModifier,
                )
            }
        }
    }
}

@Composable
internal fun BottomNavigationContent(
    component: HomePresenter,
    modifier: Modifier = Modifier,
) {
    val childStack by component.homeChildStack.collectAsState()
    val activeComponent = childStack.active.instance
    val context = LocalContext.current

    TvManiacNavigationBar(
        modifier = modifier,
    ) {
        TvManiacBottomNavigationItem(
            imageVector = Icons.Outlined.Movie,
            title = menu_item_discover.resolve(context),
            selected = activeComponent is Discover,
            onClick = { component.onDiscoverClicked() },
        )

        TvManiacBottomNavigationItem(
            imageVector = Icons.Outlined.Search,
            title = menu_item_search.resolve(context),
            selected = activeComponent is Search,
            onClick = { component.onSearchClicked() },
        )

        TvManiacBottomNavigationItem(
            imageVector = Icons.Outlined.VideoLibrary,
            title = menu_item_library.resolve(context),
            selected = activeComponent is Watchlist,
            onClick = { component.onLibraryClicked() },
        )

        TvManiacBottomNavigationItem(
            imageVector = Icons.Outlined.Settings,
            title = menu_item_settings.resolve(context),
            selected = activeComponent is Settings,
            onClick = { component.onSettingsClicked() },
        )
    }
}
