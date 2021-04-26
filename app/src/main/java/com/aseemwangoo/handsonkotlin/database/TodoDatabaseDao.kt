package com.aseemwangoo.handsonkotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDatabaseDao {
    @Query("SELECT * from my_todo_list")
    fun getAll(): LiveData<List<TodoItem>>

    @Query("SELECT * from my_todo_list where itemId = :id")
    fun getById(id: Int) : TodoItem?

    @Insert
    suspend fun insert(item:TodoItem)

    @Update
    suspend fun update(item:TodoItem)

    @Delete
    suspend fun delete(item:TodoItem)

    @Query("DELETE FROM my_todo_list")
    suspend fun deleteAllTodos()
}