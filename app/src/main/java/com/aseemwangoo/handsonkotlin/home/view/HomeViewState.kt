package com.aseemwangoo.handsonkotlin.home.view

import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel

sealed class HomeViewState {
    object Loading : HomeViewState()

    data class Loaded(
        val tasks: List<TodoItem>,
        val userModel: GoogleUserModel,
    ) : HomeViewState()

    data class Error(
        val errorText: String,
    ) : HomeViewState()
}
