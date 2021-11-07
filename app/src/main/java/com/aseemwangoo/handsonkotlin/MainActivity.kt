package com.aseemwangoo.handsonkotlin

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.work.WorkInfo
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.database.TodoViewModel
import com.aseemwangoo.handsonkotlin.database.TodoViewModelFactory
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.shared.destinations.Destinations
import com.aseemwangoo.handsonkotlin.shared.navigation.NavigationComponent
import com.aseemwangoo.handsonkotlin.ui.components.button.SimpleButtonComponent
import com.aseemwangoo.handsonkotlin.workers.OnDemandBackupViewModel
import com.aseemwangoo.handsonkotlin.workers.OnDemandBackupViewModelFactory
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationComponent()
        }

//        itemViewModel.isDone.observe(this, { status ->
//            if (status) {
//                Toast.makeText(applicationContext, "I am selected", Toast.LENGTH_SHORT).show()
//            }
//        })
    }
}

@Composable
fun HomeView(
    navController: NavController,
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
        TodoList(list = items, mTodoViewModel = mTodoViewModel)
        Spacer(modifier = Modifier.padding(top = 32.dp))
        BackupOptions(mBackUpViewModel)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoList(
    list: List<TodoItem>,
    mTodoViewModel: TodoViewModel
) {
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
}

@Composable
private fun CustomCardState(
    navController: NavController,
    mTodoViewModel: TodoViewModel
) {
    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            SimpleButtonComponent(text = ADD_TODO, onClick = {
                navController.navigate(Destinations.AddTodo)
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
