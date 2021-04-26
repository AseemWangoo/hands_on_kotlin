package com.aseemwangoo.handsonkotlin

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.aseemwangoo.handsonkotlin.components.navigation.Destinations
import com.aseemwangoo.handsonkotlin.components.navigation.NavigationComponent
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.database.TodoViewModel
import com.aseemwangoo.handsonkotlin.database.TodoViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var itemViewModel: CheckedViewModel
    private lateinit var mTodoViewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemViewModel = CheckedViewModel()
        setContent {
            NavigationComponent(itemViewModel)
        }

        itemViewModel.isDone.observe(this, { status ->
            if (status) {
                Toast.makeText(applicationContext, "I am selected", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

@Composable
fun HomeView(itemViewModel: CheckedViewModel, navController: NavController) {
    val context = LocalContext.current
    val mTodoViewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(context.applicationContext as Application)
    )

    val items = mTodoViewModel.readAllData.observeAsState(listOf()).value

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("TodoList Items")
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        CustomCardState(itemViewModel, navController)
        TodoList(list = items)
        Spacer(modifier = Modifier.padding(top = 32.dp))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoList(list: List<TodoItem>) {
    LazyColumn() {
        items(list) { todo ->
            ListItem(
                text = { Text(text = todo.itemName) },
                icon = {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null
                    )
                },
                trailing = {
                    Checkbox(
                        checked = todo.isDone,
                        onCheckedChange = {  },
                    )
                }
            )
            Divider()
        }
    }
}

// Approach 4: ViewModel
class CheckedViewModel : ViewModel() {
    private val _isDone: MutableLiveData<Boolean> = MutableLiveData(false)
    val isDone: LiveData<Boolean> = _isDone

    fun onCheckboxChange(state: Boolean) {
        _isDone.value = state
    }
}

@Composable
fun CustomCardState(itemViewModel: CheckedViewModel, navController: NavController) {
    val state: Boolean by itemViewModel.isDone.observeAsState(false)

    Column(
    ) {
        CreateCustomCard(
            title = "Dummy Item 1",
            state
        ) { itemViewModel.onCheckboxChange(it) }

        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = { navController.navigate(Destinations.AddTodo) }) {
            Text(text = "Ådd Todo")
        }
    }
}

@Composable
fun CreateCustomCard(
    title: String,
    checkboxState: Boolean,
    onCheckboxPressed: ((Boolean) -> Unit)?
) {
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkboxState,
            onCheckedChange = onCheckboxPressed,
        )
        Spacer(modifier = Modifier.padding(end = 4.dp))
        Text(text = title)
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCustomCard() {
//    CreateCustomCard(title = "Dummy Item 1", false, {})
//}