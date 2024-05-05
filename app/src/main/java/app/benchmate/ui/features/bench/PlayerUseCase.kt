package app.benchmate.ui.features.bench

import android.app.Application
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.models.Player
import app.benchmate.repositories.models.PlayerStatus
import app.benchmate.repositories.player.RealPlayerRepository
import javax.inject.Inject

/**
 * At this stage this use case simply wraps the repository interface to enable view model unit tests.
 */
class PlayerUseCase @Inject constructor(application: Application) {

    // TODO Inject this from multiplatform repository
    private val playerRepository = RealPlayerRepository(DatabaseDriverFactory(application.applicationContext))

    suspend fun getAllPlayers(): List<Player> {
        return playerRepository.getAllPlayers()
    }

    suspend fun addPlayer(
        playerId: String,
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    ) {
        playerRepository.addPlayer(
            playerId = playerId,
            firstName = firstName,
            number = number,
            playerStatus = playerStatus,
            onBenchCount = onBenchCount
        )
    }

    suspend fun updatePlayerStatus(
        playerId: String,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    ) {
        playerRepository.updatePlayerStatus(
            playerId = playerId,
            playerStatus = playerStatus,
            onBenchCount = onBenchCount
        )
    }

    suspend fun clearBenchCountAndPlayerStatus() {
        playerRepository.clearBenchCountAndPlayerStatus()
    }
}