package com.aseemwangoo.handsonkotlin.components.navigation

import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.aseemwangoo.handsonkotlin.components.navigation.Destinations.AddTodo

class NavGraph(navController: NavController) {
    val addTodo: () -> Unit = {
        navController.navigate(AddTodo)
    }
}

object Destinations {
    const val Home = "home"
    const val AddTodo = "addTodo"
}