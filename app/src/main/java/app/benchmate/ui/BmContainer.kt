package app.benchmate.ui

import androidx.compose.runtime.Composable
import app.benchmate.ui.features.bench.BenchScreen
import app.benchmate.ui.theme.BenchMateTheme

@Composable
fun BmContainer() {
    BenchMateTheme {
        // TODO Add navigation but currently just one screen
        BenchScreen()
    }
}