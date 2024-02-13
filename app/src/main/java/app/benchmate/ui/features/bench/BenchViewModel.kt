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
import app.benchmate.ui.theme.Green600
import app.benchmate.ui.theme.PurpleGrey80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenchViewModel @Inject constructor() : ViewModel() {

    private val _team = MutableStateFlow<ViewState>(ViewState.Empty())
    val team = _team

    fun addPlayerToTeam(name: String, number: Int) {
        viewModelScope.launch {
            // TODO: Send new player to repository and observe updated list rather than re-creating it here
//            val newPlayer = Player(
//                playerId = UUID.randomUUID().toString(),
//                firstName = name,
//                lastName = "",
//                games = emptyList()
//            )

            val newPlayer = PlayerDisplay(
                firstName = name,
                number = number,
                status = PlayerStatus.NONE,
                onBenchClicked = {
                    onBenchClicked(
                        number = number,
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

    private fun onBenchClicked(number: Int, status: PlayerStatus) {
        viewModelScope.launch {
            when (val state = _team.value) {
                is ViewState.Team -> {
                    val updatedTeam: List<PlayerDisplay> = state.list.map {
                        if (it.number == number) {
                            PlayerDisplay(
                                firstName = it.firstName,
                                number = number,
                                status = status,
                                onBench = if (status == PlayerStatus.BENCH) {
                                    it.onBench + 1
                                } else it.onBench,
                                onBenchClicked = {
                                    onBenchClicked(
                                        number = number,
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

    data class PlayerDisplay(
        val firstName: String,
        val number: Int,
        val status: PlayerStatus = PlayerStatus.NONE,
        val onBench: Int = 0,
        val onBenchClicked: () -> Unit
    )
}