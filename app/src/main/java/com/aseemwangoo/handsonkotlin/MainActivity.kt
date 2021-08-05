package com.aseemwangoo.handsonkotlin

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.work.WorkInfo
import com.aseemwangoo.handsonkotlin.components.backUpBtn.BackUpButton
import com.aseemwangoo.handsonkotlin.components.navigation.Destinations
import com.aseemwangoo.handsonkotlin.components.navigation.NavigationComponent
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.database.TodoViewModel
import com.aseemwangoo.handsonkotlin.database.TodoViewModelFactory
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
fun HomeView(navController: NavController) {
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
        Text("My ToDo List")
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        CustomCardState(navController, mTodoViewModel)
        TodoList(list = items, mTodoViewModel = mTodoViewModel)
        Spacer(modifier = Modifier.padding(top = 32.dp))
        BackUpButton(mBackUpViewModel)
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
fun CustomCardState(
    navController: NavController,
    mTodoViewModel: TodoViewModel
) {
    Column(
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(onClick = { navController.navigate(Destinations.AddTodo) }) {
                Text(text = "Ådd Todo")
            }
            Button(onClick = { mTodoViewModel.deleteAllTodos() }) {
                Text(text = "Clear all")
            }
        }
    }
}

// Approach 4: ViewModel
//class CheckedViewModel : ViewModel() {
//    private val _isDone: MutableLiveData<Boolean> = MutableLiveData(false)
//    val isDone: LiveData<Boolean> = _isDone
//
//    fun onCheckboxChange(state: Boolean) {
//        _isDone.value = state
//    }
//}

private fun backupDataInfoObserver(listOfWorkInfo: List<WorkInfo>) {
    if (listOfWorkInfo.isNotEmpty()) {
        val workInfo = listOfWorkInfo[0]

        if (workInfo.state.isFinished) {
            Timber.d("I AM FINISHED")
        } else {
            Timber.d("I AM IN PROGRESS")
        }
    }
}

