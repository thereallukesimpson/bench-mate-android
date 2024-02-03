package app.benchmate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.benchmate.R
import app.benchmate.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmInputDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    inputLabel: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {

    var nameEntered by remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = modifier
                    .wrapContentSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = dialogTitle,
                    style = Typography.headlineSmall,
                    modifier = modifier.padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = nameEntered,
                    onValueChange = { nameEntered = it },
                    label = { Text(text = inputLabel) },
                    singleLine = true
                )
            }
        }
    }
}

@Preview
@Composable
fun BmAddPlayerDialogPreview() {
    BmInputDialog(
        onDismissRequest = {},
        onConfirmation = {},
        dialogTitle = "Add player",
        inputLabel = "Name",
        icon = Icons.Filled.Person
    )
}
