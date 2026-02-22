package app.benchmate.ui.features.bench

import app.benchmate.repositories.models.BenchItem
import app.benchmate.repositories.models.Player
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource

object BenchTestData {

    fun getPlayers(): List<Player> {
        return listOf(
            Player(
                playerId = "1",
                firstName = "Tommy",
                number = 6,
                playerStatus = app.benchmate.repositories.models.PlayerStatus.NONE,
                benchItems = emptyList(),
                onBenchCount = 2
            ),
            Player(
                playerId = "2",
                firstName = "Bob",
                number = 12,
                playerStatus = app.benchmate.repositories.models.PlayerStatus.BENCH,
                benchItems = emptyList(),
                onBenchCount = 0
            )
        )
    }

    fun getPlayerWithCompletedBench(): List<Player> {
        val endTime = TimeSource.Monotonic.markNow()
        val startTime = endTime - 90.seconds
        return listOf(
            Player(
                playerId = "3",
                firstName = "Jane",
                number = 7,
                playerStatus = app.benchmate.repositories.models.PlayerStatus.NONE,
                benchItems = listOf(
                    BenchItem(
                        playerId = "3",
                        startTime = startTime,
                        endTime = endTime
                    )
                ),
                onBenchCount = 1
            )
        )
    }

    fun getPlayerWithActiveBench(): List<Player> {
        return listOf(
            Player(
                playerId = "4",
                firstName = "Sam",
                number = 9,
                playerStatus = app.benchmate.repositories.models.PlayerStatus.BENCH,
                benchItems = listOf(
                    BenchItem(
                        playerId = "4",
                        startTime = TimeSource.Monotonic.markNow()
                    )
                ),
                onBenchCount = 1
            )
        )
    }
}
