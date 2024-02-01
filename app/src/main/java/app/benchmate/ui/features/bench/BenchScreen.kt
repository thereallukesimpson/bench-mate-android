package app.benchmate.ui.features.bench

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import app.benchmate.ui.components.BenchMateBottomAppBar
import app.benchmate.ui.components.BenchMateFab
import app.benchmate.ui.components.BenchMateScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenchScreen() {
    BenchMateScaffold(
        bottomBar = { BenchMateBottomAppBar(
            floatingActionButton = {
                BenchMateFab(
                    onClick = {}
                )
            }
        ) }
    ) {

    }
}