package com.aseemwangoo.handsonkotlin.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<TodoItem>>
    private val repository: TodoRepository

    init {
        val todoDao = TodoDatabase.getInstance(application).todoDao()
        repository = TodoRepository(todoDao)
        readAllData = repository.readAllData
    }

    fun addTodo(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todoItem)
        }
    }
}