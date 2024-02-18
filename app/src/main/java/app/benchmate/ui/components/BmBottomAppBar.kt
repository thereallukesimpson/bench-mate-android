package app.benchmate.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import app.benchmate.R

@Composable
fun BenchMateBottomAppBar(
    floatingActionButton: @Composable (() -> Unit)? = null
) {
    val context = LocalContext.current

    BottomAppBar(
        actions = {
            IconButton(
                onClick = { /* doSomething() */ }
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Bench")
            }
            Text(text = context.getString(R.string.app_name))
//            IconButton(onClick = { /* doSomething() */ }) {
//                Icon(Icons.Filled.Person, contentDescription = "Team")
//            }
        },
        floatingActionButton = floatingActionButton
    )
}