package com.aseemwangoo.handsonkotlin.home.view

import com.aseemwangoo.handsonkotlin.database.TodoItem
import com.aseemwangoo.handsonkotlin.shared.models.Response
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
) : GetHomeTasksUseCase {

    override suspend fun invoke(): Response<List<TodoItem>> {
        return homeRepository.fetchTodos()
    }
}

interface GetHomeTasksUseCase {
    suspend operator fun invoke(): Response<List<TodoItem>>
}
