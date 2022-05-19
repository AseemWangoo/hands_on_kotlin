package com.aseemwangoo.handsonkotlin.ui.components.button

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.aseemwangoo.handsonkotlin.ui.theme.AppTheme

@Composable
fun CardActionButtonComponent(
    text: String,
    onClick: () -> Unit,
) {
    TextButton(onClick) {
        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.subtitle1,
        )
    }
}

@Preview(
    name = "Night Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Day Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Suppress("UnusedPrivateMember")
@Composable
private fun CardActionButtonComponentPreview() {
    AppTheme {
        CardActionButtonComponent(
            text = "Button",
            onClick = {}
        )
    }
}
