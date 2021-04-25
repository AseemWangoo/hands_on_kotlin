package com.aseemwangoo.handsonkotlin.components.addTodo

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

@Composable
fun AddView() {
    Scaffold(
        floatingActionButton = {
            ExtendedFAB {
                Log.i(">>>>","sfdsdfdsfdsfdsfdsfds")
            }
        }
    ) {
        InputFieldState()
    }
}

@Composable
fun InputFieldState(inputViewModel: InputViewModel = InputViewModel()) {
    val todo: String by inputViewModel.todo.observeAsState("")

    Column(modifier = Modifier.padding(16.dp)) {
        InputField(todo) { inputViewModel.onInputChange(it) }
        Spacer(modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun InputField(
    name: String,
    onValChange: ((String) -> Unit)?
) {
    if (onValChange != null) {
        TextField(
            value = name,
            placeholder = { Text(text = "Enter todo") },
            modifier = Modifier
                .padding(all = 16.dp)
                .fillMaxWidth(),
            onValueChange = onValChange
        )
    }
}

@Composable
fun ExtendedFAB(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text("Save Todo") },
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}

class InputViewModel : ViewModel() {
    private val _todo: MutableLiveData<String> = MutableLiveData("")
    val todo: LiveData<String> = _todo

    fun onInputChange(newName: String) {
        _todo.value = newName
    }
}