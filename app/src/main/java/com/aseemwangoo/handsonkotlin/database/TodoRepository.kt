package com.aseemwangoo.handsonkotlin.database

import androidx.lifecycle.LiveData

class TodoRepository(private val todoDatabaseDao: TodoDatabaseDao) {

    val readAllData : LiveData<List<TodoItem>> = todoDatabaseDao.getAll()

    suspend fun addTodo(todoItem: TodoItem) {
        todoDatabaseDao.insert(todoItem)
    }
}