package app.benchmate.ui.features.bench

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.benchmate.R
import app.benchmate.common.usecase.PlayerUseCase
import app.benchmate.ui.theme.Green600
import app.benchmate.ui.theme.PurpleGrey80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenchViewModel @Inject constructor(
    private val playerUseCase: PlayerUseCase
) : ViewModel() {

    private val _team = MutableStateFlow<ViewState>(ViewState.Empty())

    val team = _team.also {
        getPlayers()
    }

    private fun getPlayers() {
        viewModelScope.launch {
            val playersDisplay = playerUseCase.getAllPlayers().map {
                val onBenchCount =  it.onBenchCount ?: 0
                val completedBenchMs = it.benchItems
                    .filter { item -> item.endTime != null }
                    .sumOf { item -> (item.startTime.elapsedNow() - item.endTime!!.elapsedNow()).inWholeMilliseconds }
                val activeBenchStartMs = it.benchItems
                    .firstOrNull { item -> item.endTime == null }
                    ?.let { item -> System.currentTimeMillis() - item.startTime.elapsedNow().inWholeMilliseconds }
                PlayerDisplay(
                    id = it.playerId,
                    firstName = it.firstName,
                    number = it.number,
                    status = it.playerStatus?.toDisplay() ?: PlayerStatusDisplay.NONE,
                    onBench = onBenchCount,
                    completedBenchMs = completedBenchMs,
                    activeBenchStartMs = activeBenchStartMs,
                    onBenchClicked = {
                        onBenchClicked(
                            playerId = it.playerId,
                            status = if (it.playerStatus?.toDisplay() == PlayerStatusDisplay.BENCH) {
                                PlayerStatusDisplay.NONE
                            } else PlayerStatusDisplay.BENCH,
                            onBenchCount = if (it.playerStatus?.toDisplay() == PlayerStatusDisplay.NONE) {
                                onBenchCount + 1
                            } else { onBenchCount }
                        )
                    }
                )
            }
            if (playersDisplay.isNotEmpty()) {
                _team.emit(ViewState.Team(list = playersDisplay.sortedByDescending { it.status }))
            } else {
                _team.emit(ViewState.Empty())
            }
        }
    }

    fun addPlayerToTeam(name: String, number: Int) {
        viewModelScope.launch {
            playerUseCase.addPlayer(
                firstName = name,
                number = number,
                playerStatus = PlayerStatusDisplay.NONE.toDomain(),
                onBenchCount = 0
            )

            getPlayers()
        }
    }

    fun clearBench() {
        viewModelScope.launch {
            playerUseCase.clearBenchCountAndPlayerStatus()
            playerUseCase.clearBenchItems()
            getPlayers()
        }
    }

    private fun onBenchClicked(playerId: String, status: PlayerStatusDisplay, onBenchCount: Int) {
        viewModelScope.launch {
            playerUseCase.updatePlayerStatus(playerId, status.toDomain(), onBenchCount = onBenchCount)
            if (status == PlayerStatusDisplay.BENCH) {
                playerUseCase.addBenchItem(playerId)
            } else {
                playerUseCase.pauseBenchTime(playerId)
            }
            getPlayers()
        }
    }

    sealed class ViewState {
        data class Empty(
            val message: Int = R.string.empty_state_message
        ) : ViewState()

        data class Team(
            val name: String = "My Team",
            val list: List<PlayerDisplay>
        ) : ViewState()
    }

    enum class PlayerStatusDisplay(val status: String) {
        NONE("Bench"),
        BENCH("On bench"),
        PLAYING("Playing");

        fun getColour(): Color {
            return when (this) {
                NONE -> PurpleGrey80
                BENCH -> Green600
                PLAYING -> Color.Blue
            }
        }

        fun getIcon(): ImageVector {
            return when (this) {
                NONE -> Icons.Filled.Add
                BENCH -> Icons.Filled.Person
                PLAYING -> Icons.Filled.Check
            }
        }
    }

    private fun app.benchmate.repositories.models.PlayerStatus.toDisplay(): PlayerStatusDisplay {
        return when (this) {
            app.benchmate.repositories.models.PlayerStatus.NONE -> PlayerStatusDisplay.NONE
            app.benchmate.repositories.models.PlayerStatus.BENCH -> PlayerStatusDisplay.BENCH
        }
    }

    private fun PlayerStatusDisplay.toDomain(): app.benchmate.repositories.models.PlayerStatus {
        return when (this) {
            PlayerStatusDisplay.NONE -> app.benchmate.repositories.models.PlayerStatus.NONE
            PlayerStatusDisplay.BENCH -> app.benchmate.repositories.models.PlayerStatus.BENCH
            else -> app.benchmate.repositories.models.PlayerStatus.NONE
        }
    }

    data class PlayerDisplay(
        val id: String,
        val firstName: String,
        val number: Int,
        val status: PlayerStatusDisplay = PlayerStatusDisplay.NONE,
        val onBench: Int = 0,
        val completedBenchMs: Long = 0L,
        val activeBenchStartMs: Long? = null,
        val onBenchClicked: () -> Unit
    )
}