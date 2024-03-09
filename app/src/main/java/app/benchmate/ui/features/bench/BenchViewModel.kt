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
import app.benchmate.repositories.db.DatabaseDriverFactory
import app.benchmate.repositories.player.PlayerRepository
import app.benchmate.repositories.player.RealPlayerRepository
import app.benchmate.ui.theme.Green600
import app.benchmate.ui.theme.PurpleGrey80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BenchViewModel @Inject constructor() : ViewModel() {

    private val playerRepository: PlayerRepository
        get() {
            return RealPlayerRepository(DatabaseDriverFactory()) // Using JVM implementation instead of androidMain (kotlin)
        }

    private val _team = MutableStateFlow<ViewState>(ViewState.Empty())
    val team = _team

    fun getPlayers() {
        viewModelScope.launch {
            val playersDisplay = playerRepository.getAllPlayers().map {
                PlayerDisplay(
                    id = it.playerId,
                    firstName = it.firstName,
                    number = it.number,
                    status = it.playerStatus?.toDisplay() ?: PlayerStatus.NONE,
                    onBench = it.onBenchCount ?: 0,
                    onBenchClicked = {
                        onBenchClicked(
                            playerId = it.playerId,
                            status = if (it.playerStatus?.toDisplay() == PlayerStatus.BENCH) {
                                PlayerStatus.NONE
                            } else PlayerStatus.BENCH
                        )
                    }
                )
            }
            _team.emit(ViewState.Team(list = playersDisplay))
        }
    }

    fun addPlayerToTeam(name: String, number: Int) {
        viewModelScope.launch {
            // TODO: Send new player to repository and observe updated list rather than re-creating it here
//            val newPlayer = Player(
//                playerId = UUID.randomUUID().toString(),
//                firstName = name,
//                lastName = "",
//                games = emptyList()
//            )

            val id = UUID.randomUUID().toString()

            val newPlayer = PlayerDisplay(
                id = id,
                firstName = name,
                number = number,
                status = PlayerStatus.NONE,
                onBenchClicked = {
                    onBenchClicked(
                        playerId = id,
                        status = PlayerStatus.BENCH
                    )
                }
            )

            when (_team.value) {
                is ViewState.Empty -> {
                    _team.emit(
                        ViewState.Team(
                            list = listOf(newPlayer)
                        )
                    )
                }

                is ViewState.Team -> {
                    val newList = (_team.value as ViewState.Team).list + newPlayer
                    _team.emit(
                        ViewState.Team(
                            list = newList
                        )
                    )
                }
            }
        }
    }

    private fun onBenchClicked(playerId: String, status: PlayerStatus) {
        viewModelScope.launch {
            when (val state = _team.value) {
                is ViewState.Team -> {
                    val updatedTeam: List<PlayerDisplay> = state.list.map {
                        if (it.id == playerId) {
                            PlayerDisplay(
                                id = it.id,
                                firstName = it.firstName,
                                number = it.number,
                                status = status,
                                onBench = if (status == PlayerStatus.BENCH) {
                                    it.onBench + 1
                                } else it.onBench,
                                onBenchClicked = {
                                    onBenchClicked(
                                        playerId = playerId,
                                        status = if (status == PlayerStatus.BENCH) {
                                            PlayerStatus.NONE
                                        } else PlayerStatus.BENCH
                                    )
                                }
                            )
                        } else it
                    }
                    _team.emit(ViewState.Team(list = updatedTeam))
                }

                else -> {}
            }
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

    data class PlayerDisplay(
        val id: String,
        val firstName: String,
        val number: Int,
        val status: PlayerStatus = PlayerStatus.NONE,
        val onBench: Int = 0,
        val onBenchClicked: () -> Unit
    )
}