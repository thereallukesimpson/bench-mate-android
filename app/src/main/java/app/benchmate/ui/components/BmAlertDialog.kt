package app.benchmate.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import app.benchmate.ui.theme.BenchMateTheme

@Composable
fun BmAlertDialog(
    icon: ImageVector,
    title: String,
    text: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val imageVector = rememberVectorPainter(icon)

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
        },
        icon = { Icon(painter = imageVector, contentDescription = null) },
        confirmButton = {
            TextButton(
                onClick = onConfirmClick
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissClick
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun PreviewBmAlertDialog() {
    BenchMateTheme {
        BmAlertDialog(
            icon = Icons.Default.Delete,
            title = "Clear Bench",
            text = "Are you sure you want to clear the bench count? This would usually be done at the end of a game. This will not clear the players.",
            onConfirmClick = {},
            onDismissClick = {},
            onDismissRequest = {}
        )
    }
}