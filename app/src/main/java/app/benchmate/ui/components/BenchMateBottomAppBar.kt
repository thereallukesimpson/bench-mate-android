package app.benchmate.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun BenchMateBottomAppBar(
    floatingActionButton: @Composable (() -> Unit)? = null
) {
    BottomAppBar(
        actions = {
            IconButton(
                onClick = { /* doSomething() */ }
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Bench")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Person, contentDescription = "Team")
            }
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        },
        floatingActionButton = floatingActionButton
    )
}