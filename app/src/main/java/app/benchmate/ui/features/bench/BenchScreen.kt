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

    var openAlertDialog = remember { mutableStateOf(true) }

    BenchMateScaffold(
        bottomBar = {
            BenchMateBottomAppBar {
                BenchMateFab(
                    onClick = { openAlertDialog } // Not working
                )
            }
        }
    ) {
        if (openAlertDialog.value) {
            BmAlertDialog(
                onDismissRequest = {},
                onConfirmation = {},
                dialogTitle = "Add Player",
                dialogText = "Is this text needed?",
                icon = Icons.Filled.Person
            )
        }
    }
}