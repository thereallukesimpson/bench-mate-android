package app.benchmate.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.benchmate.ui.theme.BenchMateTheme
import app.benchmate.ui.theme.PurpleGrey80
import app.benchmate.ui.theme.Typography

@Composable
fun BmPlayerItem(
    modifier: Modifier = Modifier,
    firstName: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = modifier
                .padding(all = 16.dp)
        ) {
            Column {
                Text(
                    modifier = modifier.padding(bottom = 4.dp),
                    text = firstName,
                    style = Typography.bodyLarge
                )

                Text(
                    text = "Benched: 0",
                    style = Typography.bodySmall
                )
            }

            PlayerStatus(status = PlayerStatus.BENCH)
        }
    }
}

@Composable
fun PlayerStatus(
    modifier: Modifier = Modifier,
    status: PlayerStatus = PlayerStatus.NONE
) {
    val colour = when (status) {
        PlayerStatus.NONE -> PurpleGrey80
        PlayerStatus.BENCH -> Color.Yellow
        PlayerStatus.PLAYING -> Color.Green
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End

    ) {
        Card(
            modifier = modifier
                .border(width = 1.dp, color = colour)
        ) {
            Text(text = status.status)
        }
    }
}

enum class PlayerStatus(val status: String) {
    NONE(""),
    BENCH("Bench"),
    PLAYING("Playing")
}

@Preview
@Composable
fun BmPlayerItemPreview() {
    BenchMateTheme {
        BmPlayerItem(firstName = "Luke")
    }
}