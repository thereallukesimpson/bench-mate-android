package app.benchmate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
        border = BorderStroke(width = 1.dp, color = player.status.getColour())
    ) {
        Row(
            modifier = modifier
                .padding(all = 16.dp)
        ) {
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

            PlayerStatus(status = player.status)
        }
    }
}

@Composable
fun PlayerStatus(
    modifier: Modifier = Modifier,
    status: BenchViewModel.PlayerStatus = BenchViewModel.PlayerStatus.NONE
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End

    ) {
        AssistChip(
            onClick = { /*TODO*/ },
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
                status = BenchViewModel.PlayerStatus.NONE
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
                status = BenchViewModel.PlayerStatus.BENCH
            )
        )
    }
}