package app.benchmate.ui.features.bench

import android.app.Application
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.benchmate.R
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.player.RealPlayerRepository
import app.benchmate.ui.theme.Green600
import app.benchmate.ui.theme.PurpleGrey80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BenchViewModel @Inject constructor(
    application: Application,
    private val playerUseCase: PlayerUseCase
) : AndroidViewModel(application = application) {

    private val _team = MutableStateFlow<ViewState>(ViewState.Empty())

    val team = _team.also {
        getPlayers()
    }

    private fun getPlayers() {
        viewModelScope.launch {
            val playersDisplay = playerUseCase.getAllPlayers().map {
                val onBenchCount =  it.onBenchCount ?: 0
                PlayerDisplay(
                    id = it.playerId,
                    firstName = it.firstName,
                    number = it.number,
                    status = it.playerStatus?.toDisplay() ?: PlayerStatus.NONE,
                    onBench = onBenchCount,
                    onBenchClicked = {
                        onBenchClicked(
                            playerId = it.playerId,
                            status = if (it.playerStatus?.toDisplay() == PlayerStatus.BENCH) {
                                PlayerStatus.NONE
                            } else PlayerStatus.BENCH,
                            onBenchCount = if (it.playerStatus?.toDisplay() == PlayerStatus.NONE) {
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

            // TODO move UUID to repository
            val id = UUID.randomUUID().toString()
            Timber.d("UUID = $id")

            playerUseCase.addPlayer(
                playerId = id,
                firstName = name,
                number = number,
                playerStatus = PlayerStatus.NONE.toDomain(),
                onBenchCount = 0
            )

            getPlayers()
        }
    }

    fun clearBench() {
        viewModelScope.launch {
            playerUseCase.clearBenchCountAndPlayerStatus()
            getPlayers()
        }
    }

    private fun onBenchClicked(playerId: String, status: PlayerStatus, onBenchCount: Int) {
        viewModelScope.launch {
            playerUseCase.updatePlayerStatus(playerId, status.toDomain(), onBenchCount = onBenchCount)
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

    enum class PlayerStatus(val status: String) {
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

    private fun app.benchmate.repositories.models.PlayerStatus.toDisplay(): PlayerStatus {
        return when (this) {
            app.benchmate.repositories.models.PlayerStatus.NONE -> PlayerStatus.NONE
            app.benchmate.repositories.models.PlayerStatus.BENCH -> PlayerStatus.BENCH
        }
    }

    private fun PlayerStatus.toDomain(): app.benchmate.repositories.models.PlayerStatus {
        return when (this) {
            PlayerStatus.NONE -> app.benchmate.repositories.models.PlayerStatus.NONE
            PlayerStatus.BENCH -> app.benchmate.repositories.models.PlayerStatus.BENCH
            else -> app.benchmate.repositories.models.PlayerStatus.NONE
        }
    }

    data class PlayerDisplay(
        val id: String,
        val firstName: String,
        val number: Int,
        val status: PlayerStatus = PlayerStatus.NONE,
        val onBench: Int = 0,
        val onBenchClicked: () -> Unit
    )
}