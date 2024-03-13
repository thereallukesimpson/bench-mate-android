package app.benchmate.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.benchmate.ui.theme.BenchMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmTopAppBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = { Text(text = title) },
        actions = actions
    )
}

@Preview
@Composable
fun BmTopAppBarPreview() {
    BenchMateTheme {
        BmTopAppBar(
            title = "My Team",
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Reset bench",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        )
    }
}