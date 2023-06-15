package app.benchmate.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import app.benchmate.ui.theme.BenchMateTheme

@ExperimentalMaterial3Api
@Composable
fun BenchMateScaffold(
    topAppBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit =  { BenchMateBottomAppBar() },
    testTag: String? = null,
    useDarkTheme: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    BenchMateTheme(darkTheme = useDarkTheme) {
        Scaffold(
            topBar = topAppBar,
            bottomBar = bottomBar,
            content = content,
            modifier = testTag?.let {
                Modifier
                    .testTag(testTag)
            } ?: Modifier
        )
    }
}