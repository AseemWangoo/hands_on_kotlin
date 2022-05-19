package com.aseemwangoo.handsonkotlin.ui.components.card

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aseemwangoo.handsonkotlin.R
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.ui.components.button.CardActionButtonComponent
import com.aseemwangoo.handsonkotlin.ui.theme.AppTheme

@Composable
fun TodoCardComponent(
    task: TodoItem,
    onDoneClicked: (TodoItem) -> Unit = {},
) {
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = task.itemName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                style = MaterialTheme.typography.h5,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CardActionButtonComponent(
                    text = stringResource(R.string.done),
                    onClick = {
                        onDoneClicked(task)
                    },
                )
            }
        }
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
private fun TodoCardPreview() {
    val task = TodoItem(
        itemName = "Some task"
    )

    AppTheme {
        TodoCardComponent(
            task = task,
        )
    }
}
