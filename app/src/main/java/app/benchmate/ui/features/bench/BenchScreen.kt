package app.benchmate.ui.features.bench

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.benchmate.R
import app.benchmate.ui.components.BenchMateBottomAppBar
import app.benchmate.ui.components.BenchMateFab
import app.benchmate.ui.components.BenchMateScaffold
import app.benchmate.ui.components.BmAlertDialog
import app.benchmate.ui.components.BmInputDialog
import app.benchmate.ui.components.BmPlayerItem
import app.benchmate.ui.components.BmTopAppBar
import app.benchmate.ui.theme.PurpleGrey40
import app.benchmate.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenchScreen(
    viewModel: BenchViewModel = hiltViewModel()
) {
    val team by viewModel.team.collectAsState()
    val openPlayerInputDialog = remember { mutableStateOf(false) }
    val openClearBenchDialog = remember { mutableStateOf(false) }
    val showDropDownMenu = remember { mutableStateOf(false) }
    val context = LocalContext.current

    BenchMateScaffold(
        topAppBar = {
            BmTopAppBar(
                title = "My Team",
                actions = {
                    IconButton(onClick = {
                        showDropDownMenu.value = !showDropDownMenu.value
                    }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = stringResource(id = R.string.bench_options_menu_description),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    DropdownMenu(expanded = showDropDownMenu.value, onDismissRequest = { showDropDownMenu.value = false }) {
                        DropdownMenuItem(
                            text = { Text(text = stringResource(id = R.string.clear_bench)) },
                            onClick = {
                                openClearBenchDialog.value = !openClearBenchDialog.value
                                showDropDownMenu.value = !showDropDownMenu.value
                            })
                    }
                }
            )
        },
        bottomBar = {
            BenchMateBottomAppBar {
                BenchMateFab(
                    onClick = { openPlayerInputDialog.value = !openPlayerInputDialog.value }
                )
            }
        }
    ) { paddingValues ->

        when (val theTeam = team) {
            is BenchViewModel.ViewState.Empty -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Image(
                        modifier = Modifier.padding(16.dp),
                        painter = rememberVectorPainter(image = Icons.Filled.Face),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        alpha = .6f
                    )
                    Text(
                        text = context.getString(theTeam.message),
                        style = Typography.bodyLarge.copy(
                            color = PurpleGrey40
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            is BenchViewModel.ViewState.Team -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding(),
                            start = 16.dp,
                            end = 16.dp
                        ),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(count = theTeam.list.size, key = { item -> theTeam.list[item].id }) {
                            BmPlayerItem(
                                modifier = Modifier.animateItem(
                                    fadeInSpec = null,
                                    fadeOutSpec = null,
                                    placementSpec = tween(durationMillis = 500)
                                ),
                                player = theTeam.list[it]
                            )
                        }
                    }
                }
            }
        }

        if (openPlayerInputDialog.value) {
            BmInputDialog(
                onDismissRequest = { openPlayerInputDialog.value = false },
                onConfirmation = { playerName, playerNumber ->
                    viewModel.addPlayerToTeam(playerName, playerNumber)
                    openPlayerInputDialog.value = false
                },
                dialogTitle = "Add Player",
                nameInputLabel = "Name",
                numberInputLabel = "No",
                icon = Icons.Filled.Person
            )
        }

        if (openClearBenchDialog.value) {
            BmAlertDialog(
                icon = Icons.Default.Delete,
                title = "Clear Bench",
                text = "Are you sure you want to clear the bench count? This would usually be done at the end of a game. This will not clear the players.",
                onConfirmClick = {
                    viewModel.clearBench()
                    openClearBenchDialog.value = false
                },
                onDismissClick = { openClearBenchDialog.value = false },
                onDismissRequest = { openClearBenchDialog.value = false }
            )
        }
    }
}