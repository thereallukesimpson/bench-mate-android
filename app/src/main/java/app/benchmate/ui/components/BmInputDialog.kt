package app.benchmate.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.benchmate.ui.theme.Typography
import timber.log.Timber

@Composable
fun BmInputDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String, Int) -> Unit,
    dialogTitle: String,
    nameInputLabel: String,
    numberInputLabel: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {

    var nameEntered by remember {
        mutableStateOf("")
    }

    var numberEntered by remember {
        mutableStateOf("")
    }

    val imageVector = rememberVectorPainter(icon)

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
                    painter = imageVector,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = dialogTitle,
                    style = Typography.headlineSmall,
                    modifier = modifier.padding(bottom = 8.dp)
                )

                val focusRequester = remember { FocusRequester() }
                val keyboardController = LocalSoftwareKeyboardController.current

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(end = 8.dp)
                            .focusRequester(focusRequester)
                            .onFocusChanged {
                                if (it.isFocused) {
                                    keyboardController?.show()
                                }
                            },
                        value = nameEntered,
                        onValueChange = { nameEntered = it },
                        label = { Text(text = nameInputLabel) },
                        isError = !isNameValid(nameEntered),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Sentences
                        ),
                        keyboardActions = KeyboardActions {
                            if (isNameValid(nameEntered)) {
                                onConfirmation(
                                    nameEntered,
                                    checkNotNull(numberEntered.convertNumberStringToInt()))
                            }
                        }
                    )

                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }

                    OutlinedTextField(
                        modifier = Modifier
                            .weight(0.3f),
                        value = numberEntered,
                        onValueChange = { numberEntered = it },
                        label = { Text(text = numberInputLabel) },
                        isError = !isNumberValid(numberEntered),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        keyboardActions = KeyboardActions {
                            if (isNameValid(nameEntered) && isNumberValid(numberEntered)) {
                                onConfirmation(
                                    nameEntered,
                                    checkNotNull(numberEntered.convertNumberStringToInt())
                                )
                            }
                        }
                    )
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = "Cancel")
                    }

                    TextButton(onClick = {
                        if (isNameValid(nameEntered) && isNumberValid(numberEntered)) {
                            onConfirmation(
                                nameEntered,
                                checkNotNull(numberEntered.convertNumberStringToInt())
                            )
                        }
                    }) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}

private fun isNameValid(name: String): Boolean {
    return name.isNotBlank() // Must contain at least one non-whitespace character
}

private fun String.convertNumberStringToInt(): Int? {
    return try {
        this.toInt()
    } catch (e: Exception) {
        Timber.e(e, "Failed to convert number to Int")
        null
    }
}

private fun isNumberValid(number: String): Boolean {
    return when (val numberInt = number.convertNumberStringToInt()) {
        null -> {
            false
        }

        else -> {
            numberInt in 0..999
        }
    }
}

@Preview
@Composable
fun BmAddPlayerDialogPreview() {
    BmInputDialog(
        onDismissRequest = {},
        onConfirmation = { _, _ -> },
        dialogTitle = "Add player",
        nameInputLabel = "Name",
        numberInputLabel = "No",
        icon = Icons.Filled.Person
    )
}
