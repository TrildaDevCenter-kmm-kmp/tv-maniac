package com.thomaskioko.tvmaniac.watchlist.implementation

import app.cash.turbine.test
import com.thomaskioko.tvmaniac.core.base.model.AppCoroutineDispatchers
import com.thomaskioko.tvmaniac.database.test.BaseDatabaseTest
import com.thomaskioko.tvmaniac.db.Id
import com.thomaskioko.tvmaniac.db.Show_metadata
import com.thomaskioko.tvmaniac.shows.api.WatchlistDao
import com.thomaskioko.tvmaniac.util.PlatformDateFormatter
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultWatchlistDaoTest : BaseDatabaseTest() {

    private val testDispatcher = StandardTestDispatcher()
    private val coroutineDispatcher = AppCoroutineDispatchers(
        main = testDispatcher,
        io = testDispatcher,
        computation = testDispatcher,
        databaseWrite = testDispatcher,
        databaseRead = testDispatcher,
    )

    private lateinit var dao: WatchlistDao

    private val watchlistQueries
        get() = database.watchlistQueries

    private val showMetadataQueries
        get() = database.showMetadataQueries

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        dao = DefaultWatchlistDao(database, PlatformDateFormatter(), coroutineDispatcher)
        insertTestShows()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        closeDb()
    }

    @Test
    fun `should upsert show to watchlist`() {
        // Given - show exists in tvshow table from setup
        val showId = 1L

        // When
        dao.upsert(showId)

        // Then
        val watchlistItems = dao.getShowsInWatchlist()
        watchlistItems.size shouldBe 1
        watchlistItems.first().id.id shouldBe showId
        (watchlistItems.first().created_at > 0L) shouldBe true
    }

    @Test
    fun `should get shows in watchlist`() {
        // Given - add shows to watchlist
        dao.upsert(1L)
        dao.upsert(2L)

        // When
        val watchlistItems = dao.getShowsInWatchlist()

        // Then
        watchlistItems.size shouldBe 2
        watchlistItems.map { it.id.id }.toSet() shouldBe setOf(1L, 2L)
        watchlistItems.all { it.created_at > 0L } shouldBe true
    }

    @Test
    fun `should update sync state`() {
        // Given - add show to watchlist
        dao.upsert(1L)
        val showId = Id<com.thomaskioko.tvmaniac.db.TmdbId>(1L)

        // When
        dao.updateSyncState(showId)

        // Then - verify sync state is updated (we can check this through the unsynced query)
        runTest {
            dao.observeUnSyncedWatchlist().test {
                val unsyncedItems = awaitItem()
                unsyncedItems.none { it.id == 1L } shouldBe true
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `should observe shows in watchlist`() = runTest {
        // Given - add shows to watchlist
        dao.upsert(1L)
        dao.upsert(2L)

        // When & Then
        dao.observeShowsInWatchlist().test {
            val watchlistItems = awaitItem()
            watchlistItems.size shouldBe 2
            watchlistItems.map { it.id.id }.toSet() shouldBe setOf(1L, 2L)
            watchlistItems.map { it.name }.toSet() shouldBe setOf("Test Show 1", "Test Show 2")
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should observe watchlist by query`() = runTest {
        // Given - add shows to watchlist
        dao.upsert(1L)
        dao.upsert(2L)

        // When & Then - search for "Test Show 1"
        dao.observeWatchlistByQuery("Test Show 1").test {
            val searchResults = awaitItem()
            searchResults.size shouldBe 1
            searchResults.first().id.id shouldBe 1L
            searchResults.first().name shouldBe "Test Show 1"
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should observe watchlist by partial query`() = runTest {
        // Given - add shows to watchlist
        dao.upsert(1L)
        dao.upsert(2L)

        // When & Then - search for "Test" (should match both)
        dao.observeWatchlistByQuery("Test").test {
            val searchResults = awaitItem()
            searchResults.size shouldBe 2
            searchResults.map { it.id.id }.toSet() shouldBe setOf(1L, 2L)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should observe unsynced watchlist`() = runTest {
        // Given - add shows to watchlist (they start as unsynced)
        dao.upsert(1L)
        dao.upsert(2L)

        // When & Then
        dao.observeUnSyncedWatchlist().test {
            val unsyncedItems = awaitItem()
            unsyncedItems.size shouldBe 2
            unsyncedItems.map { it.id }.toSet() shouldBe setOf(1L, 2L)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `should delete show from watchlist`() {
        // Given - add shows to watchlist
        dao.upsert(1L)
        dao.upsert(2L)
        val initialCount = dao.getShowsInWatchlist().size
        initialCount shouldBe 2

        // When
        dao.delete(1L)

        // Then
        val remainingItems = dao.getShowsInWatchlist()
        remainingItems.size shouldBe 1
        remainingItems.first().id.id shouldBe 2L
    }

    @Test
    fun `should upsert show metadata`() {
        // Given - show exists in tvshow table
        val showMetadata = Show_metadata(
            show_id = Id(1L),
            season_count = 5,
            episode_count = 50,
            status = "Ended",
        )

        // When
        dao.upsert(showMetadata)

        // Then
        val metadata = showMetadataQueries.getMetadata(Id(1L)).executeAsOneOrNull()
        metadata shouldBe showMetadata
    }

    @Test
    fun `should update existing show metadata`() {
        // Given - insert initial metadata
        val initialMetadata = Show_metadata(
            show_id = Id(1L),
            season_count = 3,
            episode_count = 30,
            status = "Returning Series",
        )
        dao.upsert(initialMetadata)

        // When - update with new metadata
        val updatedMetadata = Show_metadata(
            show_id = Id(1L),
            season_count = 5,
            episode_count = 50,
            status = "Ended",
        )
        dao.upsert(updatedMetadata)

        // Then
        val metadata = showMetadataQueries.getMetadata(Id(1L)).executeAsOneOrNull()
        metadata shouldBe updatedMetadata
    }

    @Test
    fun `should include show metadata in watchlist query`() = runTest {
        // Given - add show to watchlist and add metadata
        dao.upsert(1L)
        val showMetadata = Show_metadata(
            show_id = Id(1L),
            season_count = 5,
            episode_count = 50,
            status = "Ended",
        )
        dao.upsert(showMetadata)

        // When & Then
        dao.observeShowsInWatchlist().test {
            val watchlistItems = awaitItem()
            watchlistItems.size shouldBe 1
            val item = watchlistItems.first()
            item.id.id shouldBe 1L
            item.season_count shouldBe 5L
            item.episode_count shouldBe 50L
            item.status shouldBe "Ended"
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun insertTestShows() {
        // Insert test TV shows first
        database.tvShowQueries.upsert(
            id = Id(1),
            name = "Test Show 1",
            overview = "Test overview 1",
            language = "en",
            first_air_date = "2023-01-01",
            vote_average = 8.0,
            vote_count = 100,
            popularity = 95.0,
            genre_ids = listOf(1, 2),
            status = "Returning Series",
            episode_numbers = null,
            last_air_date = null,
            season_numbers = null,
            poster_path = "/test1.jpg",
            backdrop_path = "/backdrop1.jpg",
        )

        database.tvShowQueries.upsert(
            id = Id(2),
            name = "Test Show 2",
            overview = "Test overview 2",
            language = "en",
            first_air_date = "2023-02-01",
            vote_average = 7.5,
            vote_count = 200,
            popularity = 85.0,
            genre_ids = listOf(2, 3),
            status = "Ended",
            episode_numbers = null,
            last_air_date = null,
            season_numbers = null,
            poster_path = "/test2.jpg",
            backdrop_path = "/backdrop2.jpg",
        )
    }
}
