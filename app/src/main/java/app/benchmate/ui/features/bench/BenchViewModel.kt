package app.benchmate.ui.features.bench

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.benchmate.R
import app.benchmate.repositories.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BenchViewModel @Inject constructor() : ViewModel() {

    private val _team = MutableStateFlow<ViewState>(ViewState.Empty())
    val team = _team

    fun addPlayerToTeam(name: String) {
        viewModelScope.launch {
            val newPlayer = Player(
                playerId = UUID.randomUUID().toString(),
                firstName = name,
                lastName = "",
                games = emptyList()
            )

            // TODO: Send new player to repository and observe updated list rather than re-creating it here
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
            val list: List<Player>
        ) : ViewState()
    }
}