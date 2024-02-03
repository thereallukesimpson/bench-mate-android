package app.benchmate.ui.features.bench

import android.widget.Toast
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import app.benchmate.ui.components.BenchMateBottomAppBar
import app.benchmate.ui.components.BenchMateFab
import app.benchmate.ui.components.BenchMateScaffold
import app.benchmate.ui.components.BmInputDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenchScreen(
    viewModel: BenchViewModel = hiltViewModel()
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val playerName = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    BenchMateScaffold(
        bottomBar = {
            BenchMateBottomAppBar {
                BenchMateFab(
                    onClick = { openAlertDialog.value = !openAlertDialog.value }
                )
            }
        }
    ) {
        if (openAlertDialog.value) {
            BmInputDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    viewModel.addPlayerToTeam(playerName.value)
                    Toast.makeText(context, "$playerName added", Toast.LENGTH_LONG).show()
                    openAlertDialog.value = false
                },
                dialogTitle = "Add Player",
                inputLabel = "Name",
                icon = Icons.Filled.Person
            )
        }
    }
}