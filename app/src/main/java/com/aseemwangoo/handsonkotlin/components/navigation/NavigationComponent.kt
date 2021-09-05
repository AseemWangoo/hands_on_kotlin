package com.aseemwangoo.handsonkotlin.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aseemwangoo.handsonkotlin.AuthScreen
import com.aseemwangoo.handsonkotlin.AuthView
import com.aseemwangoo.handsonkotlin.HomeView
import com.aseemwangoo.handsonkotlin.components.addTodo.AddView
import com.aseemwangoo.handsonkotlin.ui.theme.HandsOnKotlinTheme

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()

    HandsOnKotlinTheme {
        NavHost(navController = navController, startDestination = Destinations.Auth) {
            composable(Destinations.Auth) { AuthScreen()}
            composable(Destinations.Home) { HomeView(navController) }
            composable(Destinations.AddTodo) { AddView(navController) }
        }
    }
}