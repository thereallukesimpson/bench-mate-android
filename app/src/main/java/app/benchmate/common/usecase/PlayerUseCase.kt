package app.benchmate.common.usecase

import app.benchmate.repositories.models.Player
import app.benchmate.repositories.models.PlayerStatus
import app.benchmate.repositories.player.PlayerRepository
import javax.inject.Inject

class PlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {

    suspend fun getAllPlayers(): List<Player> {
        return playerRepository.getAllPlayers()
    }

    suspend fun addPlayer(
        firstName: String,
        number: Int,
        playerStatus: PlayerStatus,
        onBenchCount: Int
    ) {
        playerRepository.addPlayer(
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