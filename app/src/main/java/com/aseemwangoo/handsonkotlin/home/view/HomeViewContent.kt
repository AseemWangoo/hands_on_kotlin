package com.aseemwangoo.handsonkotlin.home.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aseemwangoo.handsonkotlin.R
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.ui.components.loader.FullScreenLoaderComponent
import com.aseemwangoo.handsonkotlin.ui.theme.AppTheme

@Composable
fun HomeViewContent(
    viewState: HomeViewState,
    onDoneClicked: (TodoItem) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        when (viewState) {
            is HomeViewState.Loading -> {
                FullScreenLoaderComponent()
            }

            is HomeViewState.Loaded -> {
                HomeViewLoaded(
                    viewState,
                    onDoneClicked
                )
            }
            else -> {
                Text(text = "In Progress")
            }
        }
    }
}

@Composable
private fun HomeViewLoaded(
    viewState: HomeViewState.Loaded,
    onDoneClicked: (TodoItem) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                shape = CircleShape,
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.addtask)
                )
            }
        },
        topBar = {
            Surface(
                color = MaterialTheme.colors.primary,
            ) {
                Column(
                    modifier = Modifier
                        .height(64.dp),
                ) {
                    Text(
                        text = "Welcome, ${viewState.userModel.name!!}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    ) {
        TodoList(
            todoTasks = viewState.tasks,
            onDoneClicked = onDoneClicked,
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
private fun HomeViewContentPreview() {
    val userModel = GoogleUserModel(
        email = "xyz",
        name = "aseem"
    )

    @Suppress("MagicNumber")
    val todoTasks = (1..10).map { index ->
        TodoItem(
            itemName = "Task $index"
        )
    }

    val viewState = HomeViewState.Loaded(
        tasks = todoTasks,
        userModel = userModel,
    )

    AppTheme {
        HomeViewContent(
            viewState = viewState,
            onDoneClicked = {},
        )
    }
}
