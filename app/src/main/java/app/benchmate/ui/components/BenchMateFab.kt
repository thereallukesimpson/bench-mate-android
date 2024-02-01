package app.benchmate.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun BenchMateFab(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    ) {
        Icon(Icons.Filled.Add, "Localized description")
    }
}