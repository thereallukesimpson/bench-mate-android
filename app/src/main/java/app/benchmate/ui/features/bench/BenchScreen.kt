package app.benchmate.ui.features.bench

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.benchmate.ui.components.BenchMateBottomAppBar
import app.benchmate.ui.components.BenchMateFab
import app.benchmate.ui.components.BenchMateScaffold
import app.benchmate.ui.components.BmAlertDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenchScreen() {

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
        if (openAlertDialog.value) {
            BmAlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {},
                dialogTitle = "Add Player",
                dialogText = "Name",
                icon = Icons.Filled.Person
            )
        }
    }
}