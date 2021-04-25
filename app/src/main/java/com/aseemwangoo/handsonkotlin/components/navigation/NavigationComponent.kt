package com.aseemwangoo.handsonkotlin.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aseemwangoo.handsonkotlin.CheckedViewModel
import com.aseemwangoo.handsonkotlin.HomeView
import com.aseemwangoo.handsonkotlin.components.addTodo.AddView
import com.aseemwangoo.handsonkotlin.ui.theme.HandsOnKotlinTheme

@Composable
fun NavigationComponent(itemViewModel: CheckedViewModel) {
    val navController = rememberNavController()

    HandsOnKotlinTheme {
        NavHost(navController = navController, startDestination = Destinations.Home) {
            composable(Destinations.Home) { HomeView(itemViewModel, navController) }
            composable(Destinations.AddTodo) { AddView() }
        }
    }
}