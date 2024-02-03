package app.benchmate.ui.features.bench

import androidx.lifecycle.ViewModel
import app.benchmate.repositories.models.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BenchViewModel @Inject constructor() : ViewModel() {

    private val _bench = MutableStateFlow<List<Player>>(emptyList())
    val bench = _bench

    fun addPlayerToTeam(name: String) {
        _bench.value += Player(
            playerId = UUID.randomUUID().toString(),
            firstName = name,
            lastName = "",
            games = emptyList()
        )
    }
}