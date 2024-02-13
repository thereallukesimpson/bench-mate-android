package app.benchmate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.benchmate.ui.features.bench.BenchViewModel
import app.benchmate.ui.theme.BenchMateTheme
import app.benchmate.ui.theme.Typography

@Composable
fun BmPlayerItem(
    modifier: Modifier = Modifier,
    player: BenchViewModel.PlayerDisplay
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(width = 2.dp, color = player.status.getColour())
    ) {
        Row(
            modifier = modifier
                .padding(all = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BmNumberCircle(number = player.number, status = player.status)

            Column {
                Text(
                    modifier = modifier.padding(bottom = 4.dp),
                    text = player.firstName,
                    style = Typography.bodyLarge
                )

                Text(
                    text = "Bench count: ${player.onBench}",
                    style = Typography.bodySmall
                )
            }

            PlayerStatus(
                status = player.status,
                onClick = player.onBenchClicked
            )
        }
    }
}

@Composable
fun PlayerStatus(
    modifier: Modifier = Modifier,
    status: BenchViewModel.PlayerStatus = BenchViewModel.PlayerStatus.NONE,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End

    ) {
        AssistChip(
            onClick = onClick,
            label = { Text(text = status.status) },
            leadingIcon = {
                Icon(
                    painter = rememberVectorPainter(image = status.getIcon()),
                    contentDescription = null
                )
            }
        )
    }
}

@Preview
@Composable
fun BmPlayerItemPreview() {
    BenchMateTheme {
        BmPlayerItem(
            player = BenchViewModel.PlayerDisplay(
                firstName = "Luke",
                number = 13,
                status = BenchViewModel.PlayerStatus.NONE,
                onBenchClicked = {}
            )
        )
    }
}

@Preview
@Composable
fun BmPlayerItemBenchPreview() {
    BenchMateTheme {
        BmPlayerItem(
            player = BenchViewModel.PlayerDisplay(
                firstName = "Luke",
                number = 14,
                status = BenchViewModel.PlayerStatus.BENCH,
                onBenchClicked = {}
            )
        )
    }
}