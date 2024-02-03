package app.benchmate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.benchmate.ui.theme.BenchMateTheme
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
        Text(
            text = firstName,
            modifier.padding(all = 16.dp),
            style = Typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun BmPlayerItemPreview() {
    BenchMateTheme {
        BmPlayerItem(firstName = "Luke")
    }
}