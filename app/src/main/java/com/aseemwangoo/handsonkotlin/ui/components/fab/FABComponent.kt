package com.aseemwangoo.handsonkotlin.ui.components.fab

import android.content.res.Configuration
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FABComponent(
    text: String,
    onClick: () -> Unit
) {
    ExtendedFloatingActionButton(
        text = { Text(text) },
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
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
fun PreviewFABComponent() {
    FABComponent(text = "Save", onClick = {})
}
