package com.aseemwangoo.handsonkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aseemwangoo.handsonkotlin.ui.theme.HandsOnKotlinTheme

class MainActivity : ComponentActivity() {

    private lateinit var itemViewModel: CheckedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemViewModel = CheckedViewModel()
        setContent {
            HandsOnKotlinTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("TodoList Items")
                        Spacer(modifier = Modifier.padding(bottom = 16.dp))
                        CustomCardState(itemViewModel)
                        Spacer(modifier = Modifier.padding(top = 32.dp))
                    }
                }
            }
        }

        itemViewModel.isDone.observe(this, { status ->
            if(status) {
                Toast.makeText(applicationContext,"I am selected", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
// Approach 4: ViewModel
class CheckedViewModel : ViewModel() {
    private val _isDone : MutableLiveData<Boolean> = MutableLiveData(false)
    val isDone : LiveData<Boolean> = _isDone

    fun onCheckboxChange(state:Boolean) {
        _isDone.value = state
    }
}

@Composable
fun CustomCardState(itemViewModel: CheckedViewModel) {
    val state : Boolean by itemViewModel.isDone.observeAsState(false)

    CreateCustomCard(
        title = "Dummy Item 1",
        state
    ) { itemViewModel.onCheckboxChange(it) }
}

//Approach 3: State Hoisting
//@Composable
//fun CustomCardState() {
//    val checkboxState = rememberSaveable { mutableStateOf(false) }
//
//    CreateCustomCard(
//        title = "Dummy Item 1",
//        checkboxState.value
//    ) { checkboxState.value = it }
//}

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
        // Approach 1
//        val checkboxState = remember { mutableStateOf(false) }

        // Approach 2 -> Persist Configuration Changes
//        val checkboxState = rememberSaveable() { mutableStateOf(false) }

        Checkbox(
            checked = checkboxState,
            onCheckedChange = onCheckboxPressed,
        )
        Spacer(modifier = Modifier.padding(end = 4.dp))
        Text(text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomCard() {
    CreateCustomCard(title = "Dummy Item 1", false, null)
}
