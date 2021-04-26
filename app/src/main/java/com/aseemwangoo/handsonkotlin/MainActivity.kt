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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.aseemwangoo.handsonkotlin.components.navigation.Destinations
import com.aseemwangoo.handsonkotlin.components.navigation.NavigationComponent
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.database.TodoViewModel
import com.aseemwangoo.handsonkotlin.database.TodoViewModelFactory

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

    val items = mTodoViewModel.readAllData.observeAsState(listOf()).value

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("My ToDo List")
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        CustomCardState(navController, mTodoViewModel)
        TodoList(list = items, mTodoViewModel = mTodoViewModel)
        Spacer(modifier = Modifier.padding(top = 32.dp))
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

//@Composable
//fun CreateCustomCard(
//    title: String,
//    checkboxState: Boolean,
//    onCheckboxPressed: ((Boolean) -> Unit)?
//) {
//    Row(
//        modifier = Modifier.padding(4.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Checkbox(
//            checked = checkboxState,
//            onCheckedChange = onCheckboxPressed,
//        )
//        Spacer(modifier = Modifier.padding(end = 4.dp))
//        Text(text = title)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCustomCard() {
//    CreateCustomCard(title = "Dummy Item 1", false, {})
//}