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
import app.benchmate.ui.theme.PurpleGrey80
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenchViewModel @Inject constructor() : ViewModel() {

    private val _team = MutableStateFlow<ViewState>(ViewState.Empty())
    val team = _team

    fun addPlayerToTeam(name: String) {
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
                status = PlayerStatus.NONE
            )

            when (_team.value) {
                is ViewState.Empty -> {
                    _team.emit(ViewState.Team(
                        list = listOf(newPlayer)
                    ))
                }

                is ViewState.Team -> {
                    val newList = (_team.value as ViewState.Team).list + newPlayer
                    _team.emit(ViewState.Team(
                        list = newList
                    ))
                }
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
                BENCH -> Color.Yellow
                PLAYING -> Color.Green
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
        val number: Int = 10,
        val status: PlayerStatus = PlayerStatus.NONE,
        val onBench: Int = 0
    )
}