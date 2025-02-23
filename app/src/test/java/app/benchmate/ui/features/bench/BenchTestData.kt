package app.benchmate.ui.features.bench

import app.benchmate.repositories.models.Player

object BenchTestData {

    fun getPlayers(): List<Player> {
        return listOf(
            Player(
                playerId = "1",
                firstName = "Tommy",
                number = 6,
                playerStatus = app.benchmate.repositories.models.PlayerStatus.NONE,
                onBenchCount = 2
            ),
            Player(
                playerId = "2",
                firstName = "Bob",
                number = 12,
                playerStatus = app.benchmate.repositories.models.PlayerStatus.BENCH,
                onBenchCount = 0
            )
        )
    }
}