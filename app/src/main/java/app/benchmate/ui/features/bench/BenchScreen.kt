package app.benchmate.ui.features.bench

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.benchmate.ui.components.BenchMateBottomAppBar
import app.benchmate.ui.components.BenchMateFab
import app.benchmate.ui.components.BenchMateScaffold
import app.benchmate.ui.components.BmInputDialog
import app.benchmate.ui.components.BmPlayerItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenchScreen(
    viewModel: BenchViewModel = hiltViewModel()
) {
    val team by viewModel.bench.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }

    BenchMateScaffold(
        bottomBar = {
            BenchMateBottomAppBar {
                BenchMateFab(
                    onClick = { openAlertDialog.value = !openAlertDialog.value }
                )
            }
        }
    ) {
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
            items(team.size) {
                BmPlayerItem(firstName = team[it].firstName)
            }
        }
        
        if (openAlertDialog.value) {
            BmInputDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = { playerName ->
                    viewModel.addPlayerToTeam(playerName)
                    openAlertDialog.value = false
                },
                dialogTitle = "Add Player",
                inputLabel = "Name",
                icon = Icons.Filled.Person
            )
        }
    }
}