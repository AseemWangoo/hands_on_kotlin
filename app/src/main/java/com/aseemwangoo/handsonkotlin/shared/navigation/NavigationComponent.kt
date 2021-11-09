package com.aseemwangoo.handsonkotlin.shared.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aseemwangoo.handsonkotlin.addtodo.view.AddTodoView
import com.aseemwangoo.handsonkotlin.auth.view.AuthScreen
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.home.view.HomeView
import com.aseemwangoo.handsonkotlin.shared.destinations.Destinations
import com.aseemwangoo.handsonkotlin.ui.theme.AppTheme
import com.squareup.moshi.Moshi

@Composable
fun NavigationComponent() {
    val navController = rememberNavController()

    AppTheme {
        NavHost(navController = navController, startDestination = Destinations.Auth) {
            composable(Destinations.Auth) { AuthScreen(navController) }
            composable(Destinations.Home) { backStackEntry ->
                val userJson = backStackEntry.arguments?.getString("user")

                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter(GoogleUserModel::class.java)
                val userObject = jsonAdapter.fromJson(userJson!!)

                HomeView(navController, userModel = userObject!!)
            }
            composable(Destinations.AddTodo) { AddTodoView(navController) }
        }
    }
}
