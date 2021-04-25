package com.aseemwangoo.handsonkotlin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.aseemwangoo.handsonkotlin.components.navigation.NavigationComponent

class MainActivity : ComponentActivity() {

    private lateinit var itemViewModel: CheckedViewModel

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
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("TodoList Items")
        Spacer(modifier = Modifier.padding(bottom = 16.dp))
        CustomCardState(itemViewModel, navController)
        Spacer(modifier = Modifier.padding(top = 32.dp))
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

    CreateCustomCard(
        title = "Dummy Item 1",
        state,
        { itemViewModel.onCheckboxChange(it) },
        navController
    )
}

@Composable
fun CreateCustomCard(
    title: String,
    checkboxState: Boolean,
    onCheckboxPressed: ((Boolean) -> Unit)?,
    navController: NavController
) {
    fun navigateToPage() {
        navController.navigate("addTodo")
    }
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
        ClickableText(text = AnnotatedString("fdfdf")) {
            navigateToPage()
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCustomCard() {
//    CreateCustomCard(title = "Dummy Item 1", false, {})
//}