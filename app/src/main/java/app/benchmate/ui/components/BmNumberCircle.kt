package app.benchmate.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.benchmate.ui.features.bench.BenchViewModel
import app.benchmate.ui.theme.Pink80

@Composable
fun BmNumberCircle(
    modifier: Modifier = Modifier,
    number: Int,
    status: BenchViewModel.PlayerStatus = BenchViewModel.PlayerStatus.NONE
) {

    Text(
        text = number.toString(),
        modifier = modifier
            .padding(24.dp)
            .drawBehind {
                drawCircle(
                    color = status.getColour(),
                    radius = 68f
                )
            }
    )

}

@Preview
@Composable
private fun BmNumberCirclePreview() {
    Column {
        BmNumberCircle(number = 8)
        BmNumberCircle(number = 56)
        BmNumberCircle(number = 934)
    }
}