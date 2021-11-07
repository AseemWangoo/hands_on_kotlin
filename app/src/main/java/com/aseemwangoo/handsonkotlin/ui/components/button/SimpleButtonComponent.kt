package com.aseemwangoo.handsonkotlin.ui.components.button

import android.content.res.Configuration
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SimpleButtonComponent(
    text: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Text(text = text)
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

@Composable
fun PreviewSimpleButton() {
    SimpleButtonComponent(text = "Button", onClick = {})
}
