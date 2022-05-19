package com.aseemwangoo.handsonkotlin.home.view

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.WorkInfo
import com.aseemwangoo.handsonkotlin.ADD_TODO
import com.aseemwangoo.handsonkotlin.TITLE_MAIN
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.database.TodoViewModel
import com.aseemwangoo.handsonkotlin.database.TodoViewModelFactory
import com.aseemwangoo.handsonkotlin.destinations.AddTodoViewDestination
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.ui.components.button.SimpleButtonComponent
import com.aseemwangoo.handsonkotlin.ui.components.card.TodoCardComponent
import com.aseemwangoo.handsonkotlin.ui.theme.AppTheme
import com.aseemwangoo.handsonkotlin.workers.OnDemandBackupViewModel
import com.aseemwangoo.handsonkotlin.workers.OnDemandBackupViewModelFactory
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination
@Composable
fun HomeView(
    navController: DestinationsNavigator,
    userModel: GoogleUserModel,
) {
    val context = LocalContext.current
    val mTodoViewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(context.applicationContext as Application)
    )

    val mBackUpViewModel: OnDemandBackupViewModel = viewModel(
        factory = OnDemandBackupViewModelFactory(context.applicationContext as Application)
    )

    val items = mTodoViewModel.readAllData.observeAsState(listOf()).value

    val listOfWorkInfo = mBackUpViewModel.backupDataInfo.observeAsState(listOf()).value
    backupDataInfoObserver(listOfWorkInfo)

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            "Welcome ${userModel.name}",
            style = MaterialTheme.typography.h4,
        )
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        Text(
            TITLE_MAIN,
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        CustomCardState(navController, mTodoViewModel)
//        TodoList(todoTasks = items, mTodoViewModel = mTodoViewModel)
        TodoList(
            todoTasks = items,
            onDoneClicked = {},
        )
        Spacer(modifier = Modifier.padding(top = 32.dp))
        BackupOptions(mBackUpViewModel)
    }
}

/*@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoList(
    list: List<TodoItem>,
    mTodoViewModel: TodoViewModel
) {f
    val context = LocalContext.current

    LazyColumn() {
        items(list) { todo ->
            val name = rememberSaveable { mutableStateOf(todo.isDone) }

            ListItem(
                text = { Text(text = todo.itemName) },
                icon = {
                    IconButton(onClick = {
                        mTodoViewModel.deleteTodo(todo)
                    }) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = null
                        )
                    }
                },
                trailing = {
                    Checkbox(
                        checked = name.value,
                        onCheckedChange = {
                            name.value = it
                            todo.isDone = name.value
                            mTodoViewModel.updateTodo(todo)

                            Toast.makeText(context, "Updated todo!", Toast.LENGTH_SHORT).show()
                        },
                    )
                }
            )
            Divider()
        }
    }
}*/

@Composable
fun TodoList(
    todoTasks: List<TodoItem>,
    onDoneClicked: (TodoItem) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(todoTasks) { task ->
            TodoCardComponent(
                task = task,
                onDoneClicked = onDoneClicked,
            )
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
private fun TodoListPreview() {
    @Suppress("MagicNumber")
    val todoTasks = (1..10).map { index ->
        TodoItem(
            itemName = "Task $index"
        )
    }
    AppTheme {
        TodoList(
            todoTasks = todoTasks,
            onDoneClicked = {},
        )
    }
}

@Composable
private fun CustomCardState(
    navController: DestinationsNavigator,
    mTodoViewModel: TodoViewModel
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SimpleButtonComponent(text = ADD_TODO, onClick = {
                navController.navigate(AddTodoViewDestination)
            })
            SimpleButtonComponent(text = "Clear all", onClick = {
                mTodoViewModel.deleteAllTodos()
            })
        }
    }
}

@Composable
private fun BackupOptions(
    mBackUpViewModel: OnDemandBackupViewModel
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        SimpleButtonComponent(text = "Backup Now", onClick = {
            mBackUpViewModel.beginBackup()
        })
        Spacer(modifier = Modifier.padding(end = 4.dp))
        SimpleButtonComponent(text = "Cancel Backup", onClick = {
            mBackUpViewModel.cancelBackup()
        })
    }
}

private fun backupDataInfoObserver(listOfWorkInfo: List<WorkInfo>) {
    if (listOfWorkInfo.isNotEmpty()) {
        val workInfo = listOfWorkInfo[0]

        if (workInfo.state == WorkInfo.State.SUCCEEDED) {
            Timber.d("✅ ✅ ✅ I AM FINISHED")
        } else if (workInfo.state == WorkInfo.State.CANCELLED) {
            Timber.d("❌ ❌ ❌ I AM CANCELLED")
        } else {
            Timber.d("I AM IN PROGRESS")
        }
    }
}
