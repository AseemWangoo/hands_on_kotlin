package com.aseemwangoo.handsonkotlin.home.view

import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.shared.models.Response

interface HomeRepository {
    suspend fun fetchTodos(): Response<List<TodoItem>>
}
