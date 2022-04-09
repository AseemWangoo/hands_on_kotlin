package com.aseemwangoo.handsonkotlin.addtodo.view

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aseemwangoo.handsonkotlin.SAVE_TODO
import com.aseemwangoo.handsonkotlin.TEST_INPUT_TAG
import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.database.TodoViewModel
import com.aseemwangoo.handsonkotlin.database.TodoViewModelFactory
import com.aseemwangoo.handsonkotlin.ui.components.fab.FABComponent
import com.aseemwangoo.handsonkotlin.ui.components.textfield.InputFieldComponent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AddTodoView(navController: DestinationsNavigator) {
    val inputViewModel = InputViewModel()
    val context = LocalContext.current
    val mTodoViewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(context.applicationContext as Application)
    )

    Scaffold(
        floatingActionButton = {
            FABComponent(text = SAVE_TODO, onClick = {
                insertTodoInDB(inputViewModel.todo.value.toString(), mTodoViewModel)

                Toast.makeText(context, "Added Todo", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            })
        }
    ) {
        InputFieldState(inputViewModel)
    }
}

@Composable
private fun InputFieldState(inputViewModel: InputViewModel) {
    val todo: String by inputViewModel.todo.observeAsState("")

    Column(modifier = Modifier.padding(16.dp)) {
        InputField(todo) { inputViewModel.onInputChange(it) }
        Spacer(modifier = Modifier.padding(10.dp))
    }
}

@Composable
private fun InputField(
    name: String,
    onValChange: ((String) -> Unit)?
) {
    val focusManager = LocalFocusManager.current

    if (onValChange != null) {
        InputFieldComponent(
            text = name,
            onChange = onValChange,
            label = "Enter todo",
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth()
                .testTag(TEST_INPUT_TAG),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
    }
}

private fun insertTodoInDB(todo: String, mTodoViewModel: TodoViewModel) {
    if (todo.isNotEmpty()) {
        val todoItem = TodoItem(
            itemName = todo,
            isDone = false
        )

        mTodoViewModel.addTodo(todoItem)
    }
}

class InputViewModel : ViewModel() {
    private val _todo: MutableLiveData<String> = MutableLiveData("")
    val todo: LiveData<String> = _todo

    fun onInputChange(newName: String) {
        _todo.value = newName
    }
}
