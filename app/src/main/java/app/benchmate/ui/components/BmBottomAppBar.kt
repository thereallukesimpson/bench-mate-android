package app.benchmate.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
            Text(text = "BenchMate")
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(Icons.Filled.Person, contentDescription = "Team")
//            }
        },
        floatingActionButton = floatingActionButton
    )
}