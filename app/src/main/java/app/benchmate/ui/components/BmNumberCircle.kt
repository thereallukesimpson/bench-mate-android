package app.benchmate.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.benchmate.ui.features.bench.BenchViewModel

@Composable
fun BmNumberCircle(
    modifier: Modifier = Modifier,
    number: Int,
    status: BenchViewModel.PlayerStatus = BenchViewModel.PlayerStatus.NONE
) {

    Box(
        modifier = modifier
    ) {
        Text(
            text = number.toString(),
            modifier = modifier
                .width(48.dp)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .drawBehind {
                    drawCircle(
                        color = status.getColour(),
                        radius = 64f
                    )
                }
        )
    }
}

@Preview
@Composable
private fun BmNumberCirclePreview() {
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        BmNumberCircle(number = 8)
        BmNumberCircle(number = 56)
        BmNumberCircle(number = 896)
    }
}